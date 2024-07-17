/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.ui.MapView
import org.pointyware.xyz.core.viewmodels.MapUiState
import org.pointyware.xyz.drive.viewmodels.RideRequestUiState

sealed interface DriveScreenState {
    data object Idle : DriveScreenState
    class NewRequest(
        val requestUiState: RideRequestUiState
    ): DriveScreenState
    data object Accepted : DriveScreenState
    data object RiderCanceled : DriveScreenState
    data object Pickup : DriveScreenState
    data object InProgress : DriveScreenState
    data object RiderCanceledLate : DriveScreenState
    data object DriverCanceled : DriveScreenState
    data object Completed : DriveScreenState

}

/**
 * Displays a map with controls for starting, monitoring, and canceling a ride.
 */
@Composable
fun DriveScreen(
    state: DriveScreenState,
    mapState: MapUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        MapView(
            state = mapState,
            modifier = Modifier.fillMaxSize()
        )

        AnimatedContent(
            targetState = state,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            when (state) {
                is DriveScreenState.Idle -> {

                }
                is DriveScreenState.NewRequest -> {
                    RideRequestView(
                        state = state.requestUiState
                    )
                }
                is DriveScreenState.Accepted -> {
                    Text("Accepted")
                }
                is DriveScreenState.RiderCanceled -> {
                    Text("RiderCanceled")
                }
                is DriveScreenState.Pickup -> {
                    Text("Pickup")
                }
                is DriveScreenState.InProgress -> {
                    Text("InProgress")
                }
                is DriveScreenState.RiderCanceledLate -> {
                    Text("RiderCanceledLate")
                }
                is DriveScreenState.DriverCanceled -> {
                    Text("DriverCanceled")
                }
                is DriveScreenState.Completed -> {
                    Text("Completed")
                }
            }
        }
    }
}
