/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.LatLong
import org.pointyware.xyz.core.ui.MapView
import org.pointyware.xyz.core.viewmodels.MapUiState
import org.pointyware.xyz.core.viewmodels.PointOfInterest

/**
 * Displays a map with controls for starting, monitoring, and canceling a ride.
 */
@Composable
fun RideScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        MapView(
            state = MapUiState(
                location = LatLong(0.0, 0.0),
                pointsOfInterest = listOf(
                    PointOfInterest.Rider(LatLong(0.0, 0.0)),
                    PointOfInterest.Origin(LatLong(0.0, 0.0)),
                    PointOfInterest.Destination(LatLong(0.0, 0.0)),
                    PointOfInterest.Driver(LatLong(0.0, 0.0)),
                ),
            ),
            modifier = modifier.fillMaxSize(),
        )

        // TODO: hoist state
        var isExpanded by remember { mutableStateOf(false) }
        RideSearchView(
            state = RideSearchViewState(
                isExpanded = isExpanded,
                query = "",
                results = emptyList(),
            ),
            modifier = Modifier.align(Alignment.BottomEnd),
            onCollapse = { isExpanded = false },
            onExpand = { isExpanded = true },
            onSearch = { query -> /* TODO: Implement search */ },
        )
    }
}
