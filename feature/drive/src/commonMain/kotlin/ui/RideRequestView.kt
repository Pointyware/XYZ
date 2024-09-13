/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
        Row {
    //        AsyncImage(
    //            model = ImageRequest(),
    //            contentDescription = "Start location image",
    //            modifier = Modifier.fillMaxWidth()
    //        )
            Text(
                text = state.riderName,
                modifier = Modifier.semantics {
                    contentDescription = "Rider Name: ${state.riderName}"
                }
            )
        }
        Row(
            modifier = Modifier.semantics(mergeDescendants = true) {}
        ) {
            Image(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Pickup Location"
            )
            Text(
                text = state.route.start.name,
            )
        }
        Row(
            modifier = Modifier.semantics(mergeDescendants = true) {
                contentDescription = "Pickup Metrics"
            }
        ) {
            Text(
                text = state.pickupDistance.format() + " @ " + state.pickupRate.format(),
            )
            Image(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null
            )
            Text(
                text = state.pickupPrice.format() + " - " + state.pickupCost.format(),
            )
        }
        Row(
            modifier = Modifier.semantics(mergeDescendants = true) {}
        ) {
            Image(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Dropoff Location"
            )
            Text(
                text = state.route.end.name,
            )
        }
        Row(
            modifier = Modifier.semantics(mergeDescendants = true) {
                contentDescription = "Route Metrics"
            }
        ) {
            Text(
                text = state.dropoffDistance.format() + " @ " + state.dropoffRate.format(),
            )
            Image(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null
            )
            Text(
                text = state.dropoffPrice.format() + " - " + state.dropoffCost.format(),
            )
        }

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
