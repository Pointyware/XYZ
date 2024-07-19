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
import org.pointyware.xyz.core.ui.MapView
import org.pointyware.xyz.core.viewmodels.MapUiState

data class RideViewState(
    val search: RideSearchViewState,
    val map: MapUiState
)

/**
 *
 */
@Composable
fun RideView(
    state: RideViewState,
    modifier: Modifier = Modifier,
    onBack: ()->Unit
) {
    Box(
        modifier = modifier
    ) {
        MapView(
            state = state.map,
            modifier = modifier.fillMaxSize(),
        )

        // TODO: hoist state
        var isExpanded by remember { mutableStateOf(false) }
        RideSearchView(
            state = state.search,
            modifier = Modifier.align(Alignment.BottomEnd),
            onCollapse = { isExpanded = false },
            onExpand = { isExpanded = true },
            onSearch = { query -> /* TODO: Implement search */ },
        )
    }
}
