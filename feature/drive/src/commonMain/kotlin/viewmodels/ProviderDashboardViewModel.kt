/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.geo.LatLong
import org.pointyware.xyz.core.viewmodels.MapViewModelImpl
import org.pointyware.xyz.drive.data.ProviderTripRepository
import org.pointyware.xyz.drive.interactors.WatchRatedRequests
import viewmodels.ProviderDashboardUiState

/**
 * View model for the driver dashboard.
 * Defines events and state for the entire lifecycle of a Trip from the driver's perspective.
 * This includes accepting and rejecting requests, managing the ride, and completing or canceling the trip.
 */
class ProviderDashboardViewModel(
    private val repository: ProviderTripRepository,
    private val watchRatedRequests: WatchRatedRequests
): MapViewModelImpl() {

    private val mutableState = MutableStateFlow<ProviderDashboardUiState>(ProviderDashboardUiState.AvailableRequests(emptyList()))
    val state: StateFlow<ProviderDashboardUiState> get() = mutableState

    init {
        watchRequests()
    }

    private val driverLocation = LatLong(0.0, 0.0) // TODO: get and update driver location

    private var requestsJob: Job? = null
    private fun watchRequests() {
        requestsJob?.cancel()
        requestsJob = viewModelScope.launch {
            watchRatedRequests.invoke()
                .onSuccess { flow ->
                    flow.collect { requestList ->
                        mutableState.value = ProviderDashboardUiState.AvailableRequests(
                            requests = requestList.map {
                                val request = it.request
                                RideRequestUiStateImpl(
                                    requestId = request.rideId,
                                    riderName = request.rider.name.given,
                                    route = request.route,
                                    riderServiceRate = request.rate,
                                    it.pickupDistance,
                                    it.driverRates
                                )
                            }
                        )
                    }
                }
                .onFailure {
                    TODO("Handle failure")
                }
        }
    }

    private fun stopWatchingRequests() {
        requestsJob?.cancel()
    }

    fun onAccept(request: Uuid) {
        viewModelScope.launch {
            repository.acceptRequest(request)
                .onSuccess {
                    stopWatchingRequests()
                    mutableState.value = ProviderDashboardUiState.Accepted(it)
                }
                .onFailure {
                    TODO("Handle failure")
                }
        }
    }

    fun onReject(request: Uuid) {
        viewModelScope.launch {
            repository.rejectRequest(request)
                .onSuccess {

                }
                .onFailure {
                    TODO("Handle failure")
                }
        }
    }

    fun onPickUpRider() {
        viewModelScope.launch {
            repository.pickUpRider()
                .onSuccess {

                }
                .onFailure {

                }
        }
    }
}
