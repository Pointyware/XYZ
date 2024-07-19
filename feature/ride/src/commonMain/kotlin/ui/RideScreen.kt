/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.LatLong
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.core.ui.MapView
import org.pointyware.xyz.core.viewmodels.MapUiState
import org.pointyware.xyz.core.viewmodels.PointOfInterest
import org.pointyware.xyz.feature.ride.viewmodels.RideViewModel

data class RideScreenState(
    val search: RideSearchViewState,
    val mapState: MapUiState
)

/**
 * Displays a map with controls for starting, monitoring, and canceling a ride.
 */
@Composable
fun RideScreen(
    viewModel: RideViewModel,
    navController: XyzNavController,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state.collectAsState()
    val mapState = viewModel.mapState.collectAsState()

    val rideScreenState = RideUiStateMapper.map(state.value to mapState.value)
    Box(
        modifier = modifier
    ) {
        MapView(
            state = rideScreenState.mapState,
            modifier = modifier.fillMaxSize(),
        )

        // TODO: hoist state
        var isExpanded by remember { mutableStateOf(false) }
        RideSearchView(
            state = rideScreenState.search,
            modifier = Modifier.align(Alignment.BottomEnd),
            onCollapse = { isExpanded = false },
            onExpand = { isExpanded = true },
            onSearch = { query -> /* TODO: Implement search */ },
        )
    }
}
