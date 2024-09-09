/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pointyware.xyz.core.entities.ride.Location
import org.pointyware.xyz.feature.ride.viewmodels.RideUiState

data class RideSearchViewState(
    val isExpanded: Boolean,
    val query: String,
    val results: List<String>,
)

/**
 * Presents like a Floating-Action-Button when collapsed and a search bar when expanded.
 */
@Composable
fun RideSearchView(
    state: RideUiState,
    modifier: Modifier = Modifier,
    onNewRide: ()->Unit,
    onUpdateSearch: (String)-> Unit,
    onSendQuery: ()->Unit,
    onSelectLocation: (Location)->Unit,
    onConfirmDetails: ()->Unit,
    onCancelRequest: ()->Unit
) {
    val shape = when (state) {
        is RideUiState.Idle,
        is RideUiState.Waiting -> {
            CircleShape
        }
        else -> {
            // Rounded corners
            RoundedCornerShape(8.dp)
        }
    }
    Surface(
        modifier = modifier.background(color = MaterialTheme.colorScheme.primary, shape = shape)
    ) {
        AnimatedContent(targetState = state) { state ->
            when (state) {
                is RideUiState.Idle -> {
                    Button(onClick = onNewRide) {
                        Text("New Ride")
                    }
                }

                is RideUiState.Search -> {
                    Row {
                        TextField(
                            value = state.query,
                            onValueChange = onUpdateSearch,
                            label = { Text("Search") },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Button(
                            onClick = {
                                onSendQuery()
                            },
                            enabled = state.query.isNotBlank()
                        ) {
                            Text("Confirm")
                        }
                        var expanded by remember { mutableStateOf(state.suggestions.isNotEmpty()) }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            state.suggestions.forEach { suggestion ->
                                DropdownMenuItem(
                                    text = { Text(suggestion.name) },
                                    onClick = {
                                        expanded = false
                                        onSelectLocation(suggestion)
                                    }
                                )
                            }
                        }
                    }
                }

                is RideUiState.Confirm -> {
                    // TODO: confirm ride details
                    Button(onClick = onConfirmDetails) {
                        Text("Confirm")
                    }
                }

                is RideUiState.Posted -> {
                    Text("Hailing a driver")
                    Button(onClick = onCancelRequest) {
                        Text("Cancel")
                    }
                }

                is RideUiState.Waiting -> {
                    Text("Waiting for driver")
                    // TODO: rider details
                }

                is RideUiState.Riding -> {
                    // Do nothing
                    // TODO: rider details
                }
            }
        }
    }
}
