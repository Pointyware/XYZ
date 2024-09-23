/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.feature.ride.entities.PaymentMethod
import org.pointyware.xyz.feature.ride.viewmodels.PassengerDashboardUiState
import org.pointyware.xyz.feature.ride.ui.PaymentSelectionView

data class TripSearchViewState(
    val isExpanded: Boolean,
    val query: String,
    val results: List<String>,
)

/**
 * Presents like a Floating-Action-Button when collapsed and a search bar when expanded.
 */
@Composable
fun TripSearchView(
    state: PassengerDashboardUiState,
    modifier: Modifier = Modifier,
    onNewTrip: ()->Unit,
    onUpdateSearch: (String)-> Unit,
    onSendQuery: ()->Unit,
    onSelectLocation: (Location)->Unit,
    onConfirmDetails: ()->Unit,
    onCancelRequest: ()->Unit,
    onSelectPayment: () -> Unit,
    onPaymentSelected: (PaymentMethod) -> Unit
) {
    val shape = when (state) {
        is PassengerDashboardUiState.Idle,
        is PassengerDashboardUiState.Waiting -> {
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
        AnimatedContent(
            targetState = state,
            contentKey = { it::class }
        ) { state ->
            when (state) {
                is PassengerDashboardUiState.Idle -> {
                    IdleSearchView(onNewRide = onNewTrip)
                }

                is PassengerDashboardUiState.Search -> {
                    ActiveSearchView(
                        state = state,
                        onUpdateSearch = onUpdateSearch,
                        onSendQuery = onSendQuery,
                        onSelectLocation = onSelectLocation,
                        onSelectPayment = onSelectPayment,
                        onPaymentSelected = onPaymentSelected,
                    )
                }

                is PassengerDashboardUiState.Confirm -> {
                    SearchDetailsView(
                        state = state,
                        onConfirmDetails = onConfirmDetails,
                    )
                }

                is PassengerDashboardUiState.Posted -> {
                    PostedRideView(
                        state = state,
                        onCancelRequest = onCancelRequest
                    )
                }

                is PassengerDashboardUiState.Waiting -> {
                    AwaitingRideView(state = state)
                }

                is PassengerDashboardUiState.Riding -> {
                    ActiveRideView(state = state)
                }
            }
        }
    }
}

@Composable
fun IdleSearchView(
    onNewRide: ()->Unit
) {
    Button(onClick = onNewRide) {
        Text("New Ride")
    }
}

@Composable
fun ActiveSearchView(
    state: PassengerDashboardUiState.Search,
    onUpdateSearch: (String)->Unit,
    onSendQuery: ()->Unit,
    onSelectLocation: (Location)->Unit,
    onSelectPayment: ()->Unit,
    onPaymentSelected: (PaymentMethod)->Unit
) {
    Column {
        PaymentSelectionView(
            state = state.paymentSelection,
            onSelectPayment = onSelectPayment,
            onPaymentSelected = onPaymentSelected
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = state.query,
                onValueChange = onUpdateSearch,
                label = { Text("Search") },
                modifier = Modifier.weight(1f),
            )
            Button(
                onClick = {
                    onSendQuery()
                },
                enabled = state.query.isNotBlank()
            ) {
                Text("Confirm")
            }
            var expanded by remember(state.suggestions) { mutableStateOf(state.suggestions.isNotEmpty()) }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.semantics { contentDescription = "Location Suggestions" },
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
}

@Composable
fun SearchDetailsView(
    state: PassengerDashboardUiState.Confirm,
    onConfirmDetails: ()->Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "from: ${state.origin.name}", // TODO: add search field for origin
        )
        Text(
            text = "to: ${state.destination.name}", // TODO: use search field for destination
        )
        val distanceString = state.route?.let { route -> "${route.distance}" } ?: "Calculating..."
        Text(
            text = "distance: $distanceString",
        )
        val priceString = state.price?.let { price -> "$price" } ?: "Calculating..."
        Text(
            text = "price: $priceString",
        )
        Button(
            onClick = onConfirmDetails,
            enabled = state.route != null && state.price != null
        ) {
            Text("Confirm Route")
        }
    }
}

@Composable
fun PostedRideView(
    state: PassengerDashboardUiState.Posted,
    onCancelRequest: ()->Unit
) {
    Column {
        Text("Hailing a driver")
        Button(onClick = onCancelRequest) {
            Text("Cancel Request")
        }
    }
}

@Composable
fun AwaitingRideView(
    state: PassengerDashboardUiState.Waiting
) {
    Text("Waiting for driver")
    // TODO: rider details
}

@Composable
fun ActiveRideView(
    state: PassengerDashboardUiState.Riding
) {
    // Do nothing
    // TODO: rider details
}
