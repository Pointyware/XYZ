/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.viewmodels

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.xyz.core.entities.business.dollarCents
import org.pointyware.xyz.core.entities.geo.LengthUnit
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.viewmodels.LoadingUiState
import org.pointyware.xyz.core.viewmodels.MapViewModelImpl
import org.pointyware.xyz.core.viewmodels.postError
import org.pointyware.xyz.feature.ride.data.PaymentRepository
import org.pointyware.xyz.feature.ride.data.TripEvent
import org.pointyware.xyz.feature.ride.data.TripRepository
import org.pointyware.xyz.feature.ride.entities.PaymentMethod
import org.pointyware.xyz.feature.ride.ui.PaymentSelectionViewState

/**
 * Maintains the state of a rider UI and provides actions to update it.
 *
 * @see PassengerDashboardUiState
 */
class TripViewModel(
    private val tripRepository: TripRepository,
    private val paymentRepository: PaymentRepository
): MapViewModelImpl() {

    private val userLocation = Location(
        lat = 36.1031637, long = -97.0517528,
        name = "Mission of Hope",
        address = "1804 S Perkins Rd", zip = "74074"
    )

    private val mutableLoadingState = MutableStateFlow<LoadingUiState<Unit>>(LoadingUiState.Idle())
    val loadingState: StateFlow<LoadingUiState<Unit>> get() = mutableLoadingState
    private val mutableState = MutableStateFlow<PassengerDashboardUiState>(PassengerDashboardUiState.Idle)
    val state: StateFlow<PassengerDashboardUiState> get() = mutableState

    fun startSearch() {
        mutableState.value = PassengerDashboardUiState.Search("", emptyList(), PaymentSelectionViewState.PaymentSelected(null))
    }

    fun updateQuery(query: String) {
        mutableState.update {
            if (it is PassengerDashboardUiState.Search) {
                it.copy(query = query)
            } else {
                it
            }
        }
    }

    fun sendQuery() {
        mutableState.update {
            if (it is PassengerDashboardUiState.Search) {
                it.copy(suggestions = listOf(
                    Location(lat = 36.1314561, long = -97.0605216, name = "Red Rock Bakery", address = "910 N Boomer Rd", zip = "74075"),
                    Location(lat = 36.1244264, long = -97.0583594, name = "Sonic", address = "215 N Main St", zip = "74075"),
                    Location(lat = 36.1171898, long = -97.0509852, name = "Sonic", address = "423 S Perkins Rd", zip = "74074"),
                    Location(lat = 36.1150974, long = -97.1177298, name = "Sonic", address = "4425 W 6th Ave", zip = "74074"),
                ))
            } else {
                it
            }
        }
    }

    private fun findRoute(start: Location, end: Location) {
        mutableLoadingState.value = LoadingUiState.Loading()
        viewModelScope.launch {
            tripRepository.findRoute(start, end)
                .onSuccess { route ->
                    // TODO: Calculate route and price; update state
                    val rate = 120 // $1.20 per km
                    val price = (route.distance.to(LengthUnit.KILOMETERS).value * rate).toLong().dollarCents()
                    mutableState.update {
                        if (it is PassengerDashboardUiState.Confirm) {
                            it.copy(route = route, price = price)
                        } else {
                            it
                        }
                    }
                    mutableLoadingState.value = LoadingUiState.Idle()
                }
                .onFailure {
                    mutableLoadingState.postError(it)
                }
        }
    }

    fun selectLocation(location: Location) {
        mutableState.update {
            if (it is PassengerDashboardUiState.Search) {
                PassengerDashboardUiState.Confirm(
                    origin = userLocation,
                    destination = location,
                    route = null,
                    price = null
                ).also {
                    findRoute(userLocation, location)
                }
            } else {
                it
            }
        }
    }

    fun confirmDetails() {
        viewModelScope.launch {
            mutableState.update { uiState ->
                if (uiState is PassengerDashboardUiState.Confirm) {
                    val route = uiState.route ?: return@launch
                    val price = uiState.price ?: return@launch
                    tripRepository.requestRide(uiState.route)
                        .onFailure {
                            it.printStackTrace()
                            return@launch
                        }
                    PassengerDashboardUiState.Posted(
                        route = route,
                        price = price
                    ).also {
                        watchDriverAcceptance()
                    }
                } else {
                    uiState
                }
            }
        }
    }

    private var driverAcceptanceJob: Job? = null
    private fun watchDriverAcceptance() {
        driverAcceptanceJob?.cancel()
        driverAcceptanceJob = viewModelScope.launch {
            tripRepository.tripEvents.collect { event ->
                when (event) {
                    is TripEvent.Accepted -> {
                        mutableState.update {
                            if (it is PassengerDashboardUiState.Posted) {
//                                val eta  = event.pendingRide.route.eta // TODO: create eta use case
                                PassengerDashboardUiState.Waiting(
                                    driver = event.driverProfile,
                                    eta = 0,
                                    route = it.route
                                )
                            } else {
                                it
                            }
                        }
                    }
                    is TripEvent.PickedUp -> {
                        mutableState.update {
                            if (it is PassengerDashboardUiState.Waiting) {
//                                val eta  = event.pendingRide.route.eta // TODO: create eta use case
                                PassengerDashboardUiState.Riding(
                                    driver = event.driverProfile,
                                    eta = 0,
                                    route = it.route
                                )
                            } else {
                                it
                            }
                        }
                    }
                    is TripEvent.DroppedOff -> {
                        mutableState.update {
                            if (it is PassengerDashboardUiState.Riding) {
                                PassengerDashboardUiState.Arrived(
                                    driver = event.driverProfile,
                                    route = it.route
                                )
                            } else {
                                it
                            }
                        }
                    }
                }
            }
        }
    }

    fun cancelRide() {
        // TODO: send cancellation request to server
        mutableState.update {
            PassengerDashboardUiState.Idle
        }
    }

    fun clearError() {
        mutableLoadingState.value = LoadingUiState.Idle()
    }

    fun onSelectPayment() {
        viewModelScope.launch {
            paymentRepository.getPaymentMethods()
                .onSuccess { methods ->
                    mutableState.update {
                        if (it is PassengerDashboardUiState.Search) {
                            it.copy(paymentSelection = PaymentSelectionViewState.SelectPayment(methods))
                        } else {
                            it
                        }
                    }
                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun onPaymentSelected(paymentMethod: PaymentMethod) {
        mutableState.update {
            if (it is PassengerDashboardUiState.Search) {
                it.copy(paymentSelection = PaymentSelectionViewState.PaymentSelected(paymentMethod))
            } else {
                it
            }
        }
    }

    fun onCancelTrip() {
        TODO("Not yet implemented")
    }

    fun onRateDriver() {
        TODO("Not yet implemented")
    }

    fun onFinishTrip() {
        mutableState.update {
            if (it is PassengerDashboardUiState.Arrived) {
                PassengerDashboardUiState.Idle
            } else {
                it
            }
        }
    }

    override fun dispose() {
        super.dispose()
        driverAcceptanceJob?.cancel()
    }
}
