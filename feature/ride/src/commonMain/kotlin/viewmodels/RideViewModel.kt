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
        mutableState.value = RideUiState.Search("", emptyList())
    }

    fun updateQuery(query: String) {
        mutableState.update {
            if (it is RideUiState.Search) {
                it.copy(query = query)
            } else {
                it
            }
        }
    }

    fun sendQuery() {
        mutableState.update {
            if (it is RideUiState.Search) {
                it.copy(suggestions = listOf(
                    Location(0.0, 0.0, "123 Main St"),
                    Location(0.0, 0.0, "456 Main St"),
                    Location(0.0, 0.0, "789 Main St"),
                    Location(0.0, 0.0, "012 Main St"),
                    Location(0.0, 0.0, "345 Main St"),
                ))
            } else {
                it
            }
        }
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
