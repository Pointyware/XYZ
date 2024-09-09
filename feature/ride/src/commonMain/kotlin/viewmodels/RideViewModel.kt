/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.pointyware.xyz.core.entities.ride.Location
import org.pointyware.xyz.core.viewmodels.MapViewModelImpl

/**
 * Maintains the state of a rider UI and provides actions to update it.
 *
 * @see RideUiState
 */
class RideViewModel(

): MapViewModelImpl() {

    private val mutableState = MutableStateFlow<RideUiState>(RideUiState.Idle)
    val state: StateFlow<RideUiState> get() = mutableState

    fun startSearch() {
        TODO("State must be Idle; move to empty search state")
    }

    fun updateSearch(query: String) {
        TODO("State must be Search; update search query; after a delay, send query for suggestions")
    fun updateQuery(query: String) {
        }
    }

    fun sendQuery() {
        TODO("State must be Search; send the query to the server and update the search results")
    }

    fun selectLocation(location: Location) {
        TODO("State must be Search; select a location from the search results")
    }

    fun confirmDetails() {
        TODO("State must be Confirm; confirm the ride details and post the ride")
    }

    fun cancelRide() {
        TODO("State must be Waiting or Riding; cancel the ride")
    }
}
