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
import org.pointyware.xyz.core.entities.ride.Location
import org.pointyware.xyz.core.ui.AdView
import org.pointyware.xyz.core.ui.AdViewState
import org.pointyware.xyz.core.ui.MapView
import org.pointyware.xyz.core.viewmodels.MapUiState
import org.pointyware.xyz.feature.ride.viewmodels.RideUiState

data class RideViewState(
    val ride: RideUiState,
    val map: MapUiState
)

/**
 *
 */
@Composable
fun RideView(
    state: RideViewState,
    modifier: Modifier = Modifier,
    onStartSearch: ()->Unit,
    onSendQuery: (String)->Unit,
    onSelectLocation: (Location)->Unit,
    onConfirmDetails: ()->Unit,
    onCancel: ()->Unit,
    onBack: ()->Unit
) {
    Box(
        modifier = modifier
    ) {
        MapView(
            state = state.map,
            modifier = modifier.fillMaxSize(),
        )

        AdView(
            state = AdViewState(unit = "ca-app-pub-7815756395963999/4805080719"),
            modifier = Modifier.align(Alignment.TopCenter)
        )

        var query by remember { mutableStateOf("") }
        RideSearchView(
            state = state.ride,
            modifier = Modifier.align(Alignment.BottomEnd),
            onNewRide = onStartSearch,
            onUpdateSearch = { query = it },
            onSendQuery = { onSendQuery(query) },
            onSelectLocation = onSelectLocation,
            onConfirmDetails = onConfirmDetails,
            onCancelRequest = onCancel
        )
    }
}
