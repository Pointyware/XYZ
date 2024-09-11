/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.viewmodels.MapViewModelImpl
import org.pointyware.xyz.drive.SimpleRideFilter
import org.pointyware.xyz.drive.data.RideRepository
import org.pointyware.xyz.drive.ui.DriveScreenState

/**
 *
 */
class DriveViewModel(
    private val repository: RideRepository
): MapViewModelImpl() {

    private val mutableState = MutableStateFlow<DriveScreenState>(DriveScreenState.AvailableRequests(emptyList()))
    val state: StateFlow<DriveScreenState> get() = mutableState

    init {
        watchRequests()
    }

    private var requestsJob: Job? = null
    private fun watchRequests() {
        requestsJob?.cancel()
        requestsJob = viewModelScope.launch {
            repository.watchRequests(SimpleRideFilter.Permissive)
                .onSuccess { flow ->
                    flow.collect { requestList ->
                        mutableState.value = DriveScreenState.AvailableRequests(
                            requests = requestList.map {
                                RideRequestUiStateImpl(
                                    requestId = it.rideId,
                                    riderName = it.rider.name.given,
                                    destination = it.route.end,
                                    distanceFromDriver = it.route.distance,
                                    distanceOfRoute = it.route.distance,
                                    riderServiceRate = it.rate
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
                    mutableState.value = DriveScreenState.Accepted(it)
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
}
