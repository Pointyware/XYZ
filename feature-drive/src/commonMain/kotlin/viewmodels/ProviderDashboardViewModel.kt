/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.geo.LatLong
import org.pointyware.xyz.core.viewmodels.MapViewModelImpl
import org.pointyware.xyz.drive.data.ProviderTripRepository
import org.pointyware.xyz.drive.interactors.WatchRatedRequests
import org.pointyware.xyz.drive.org.pointyware.xyz.drive.interactors.WatchProviderDistance
import viewmodels.ProviderDashboardUiState
import kotlin.uuid.ExperimentalUuidApi

/**
 * View model for the driver dashboard.
 * Defines events and state for the entire lifecycle of a Trip from the driver's perspective.
 * This includes accepting and rejecting requests, managing the ride, and completing or canceling the trip.
 */
@OptIn(ExperimentalUuidApi::class)
class ProviderDashboardViewModel(
    private val repository: ProviderTripRepository,
    private val watchRatedRequests: WatchRatedRequests,
    private val watchProviderDistance: WatchProviderDistance,
): MapViewModelImpl() {

    private val mutableState = MutableStateFlow<ProviderDashboardUiState>(ProviderDashboardUiState.AvailableRequests(emptyList()))
    val state: StateFlow<ProviderDashboardUiState> get() = mutableState

    init {
        watchRequests()
    }

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
                    it.printStackTrace()
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
                    mutableState.value = ProviderDashboardUiState.Accepted(it, false)
                    watchDistance(it.plannedRoute.start.coordinates)
                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun onReject(request: Uuid) {
        viewModelScope.launch {
            repository.rejectRequest(request)
                .onSuccess {

                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    private var distanceJob: Job? = null
    private fun watchDistance(origin: LatLong) {
        distanceJob?.cancel()
        distanceJob = viewModelScope.launch {
            watchProviderDistance.invoke(origin)
                .collect { withinDistance ->
                    mutableState.update {
                        when (it) {
                            is ProviderDashboardUiState.Accepted -> it.copy(atOrigin = withinDistance)
                            is ProviderDashboardUiState.InProgress -> it.copy(atDestination = withinDistance)
                            else -> it
                        }
                    }
                }
        }
    }

    private fun stopWatchingDistance() {
        distanceJob?.cancel()
    }

    fun onPickUpRider() {
        stopWatchingDistance()
        viewModelScope.launch {
            repository.pickUpRider()
                .onSuccess {
                    mutableState.value = ProviderDashboardUiState.InProgress(it, false)
                    watchDistance(it.plannedRoute.end.coordinates)
                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun onDropOffRider() {
        stopWatchingDistance()
        viewModelScope.launch {
            repository.completeRide()
                .onSuccess {
                    mutableState.value = ProviderDashboardUiState.Completed
                }
                .onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun onConfirmCompletion() {
        mutableState.value = ProviderDashboardUiState.AvailableRequests(emptyList())
        watchRequests()
    }

    override fun dispose() {
        super.dispose()
        stopWatchingRequests()
        stopWatchingDistance()
    }
}
