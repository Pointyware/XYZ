/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.drive.viewmodels.RideRequestUiState

/**
 * Displays information about a new ride request: distance from driver, distance of route, and rider service rate.
 */
@Composable
fun RideRequestView(
    state: RideRequestUiState,
    modifier: Modifier = Modifier,
    onReject: ()->Unit,
    onAccept: ()->Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text("New Ride Request")
        Text(state.riderName)
        Text(state.route.start.name)
        Text(state.route.end.name)
        Text(state.distanceFromDriver.toString())
        Text(state.route.distance.toString())
        Text(state.totalFair.toString())

        Row {
            Button(onClick = onReject) {
                Text("Reject")
            }
            Button(onClick = onAccept) {
                Text("Accept")
            }
        }
    }
}
