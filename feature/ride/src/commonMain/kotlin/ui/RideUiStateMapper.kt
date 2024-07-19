/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import org.pointyware.xyz.core.common.Mapper
import org.pointyware.xyz.core.entities.LatLong
import org.pointyware.xyz.core.viewmodels.MapUiState
import org.pointyware.xyz.feature.ride.viewmodels.RideUiState

/**
 *
 */
object RideUiStateMapper: Mapper<Pair<RideUiState, MapUiState>, RideViewState> {
    override fun map(input: Pair<RideUiState, MapUiState>): RideViewState {
        val (state, mapState) = input
        return RideViewState(
            search = RideSearchViewState(
                query = "",
                results = emptyList(),
                isExpanded = false // TODO: map state
            ),
            map = mapState
        )
    }
}
