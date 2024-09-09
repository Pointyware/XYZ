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

    private val userLocation = Location(
        lat = 36.1031637, long = -97.0517528,
        name = "Mission of Hope",
        address = "1804 S Perkins Rd", zip = "74074"
    )

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

    fun selectLocation(location: Location) {
        mutableState.update {
            if (it is RideUiState.Search) {
                RideUiState.Confirm(
                    origin = userLocation,
                    destination = location,
                    route = null,
                    price = null
                )
                // TODO: Calculate route and price; update state
            } else {
                it
            }
        }
    }

    fun confirmDetails() {
        mutableState.update {
            if (it is RideUiState.Confirm) {
                val route = it.route ?: return
                val price = it.price ?: return
                RideUiState.Posted(
                    route = route,
                    price = price
                )
                // TODO: listen for driver acceptance; update state
            } else {
                it
            }
        }
    }

    fun cancelRide() {
        // TODO: send cancellation request to server
        mutableState.update {
            RideUiState.Idle
        }
    }
}
