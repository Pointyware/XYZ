/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import org.pointyware.xyz.core.common.Mapper
import org.pointyware.xyz.core.viewmodels.MapUiState
import org.pointyware.xyz.feature.ride.viewmodels.PassengerDashboardUiState

/**
 *
 */
object RideUiStateMapper: Mapper<Pair<PassengerDashboardUiState, MapUiState>, PassengerDashboardViewState> {
    override fun map(input: Pair<PassengerDashboardUiState, MapUiState>): PassengerDashboardViewState {
        val (state, mapState) = input
        return PassengerDashboardViewState(
            ride = state,
            map = mapState
        )
    }
}
