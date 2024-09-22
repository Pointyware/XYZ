/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.ride.PendingRide
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.core.ui.AdView
import org.pointyware.xyz.core.ui.AdViewState
import org.pointyware.xyz.core.ui.MapView
import org.pointyware.xyz.core.viewmodels.MapUiState
import org.pointyware.xyz.drive.viewmodels.ProviderDashboardViewModel
import org.pointyware.xyz.drive.viewmodels.RideRequestUiState

/**
 * Represents the state of the provider dashboard.
 */
sealed interface ProviderDashboardScreenState {
    /**
     * The driver is available to accept requests is waiting for a new request.
     */
    data class AvailableRequests(
        val requests: List<RideRequestUiState>
    ): ProviderDashboardScreenState

    /**
     * The driver has accepted a request and is on their way to pick up the passenger.
     */
    data class Accepted(
        val ride: PendingRide
    ): ProviderDashboardScreenState

    /**
     * The rider has canceled the request before the driver arrived.
     */
    data object RiderCanceled : ProviderDashboardScreenState

    data object InProgress : ProviderDashboardScreenState
    data object RiderCanceledLate : ProviderDashboardScreenState
    data object DriverCanceled : ProviderDashboardScreenState
    data object Completed : ProviderDashboardScreenState

}

/**
 * Displays a map with controls for managing trip requests and ride status.
 */
@Composable
fun ProviderDashboardScreen(
    viewModel: ProviderDashboardViewModel,
    navController: XyzNavController,
) {
    val state: ProviderDashboardScreenState by viewModel.state.collectAsState()
    val mapState: MapUiState by viewModel.mapState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MapView(
            state = mapState,
            modifier = Modifier.fillMaxSize()
        )

        AdView(
            state = AdViewState(unit = "ca-app-pub-7815756395963999/7475476086"),
            modifier = Modifier.align(Alignment.TopCenter)
        )

        AnimatedContent(
            targetState = state,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            when (val capture = state) {
                is ProviderDashboardScreenState.AvailableRequests -> {
                    RideRequestList(
                        requests = capture.requests,
                        onAccept = { viewModel.onAccept(it) },
                        onReject = { viewModel.onReject(it) },
                    )
                }
                is ProviderDashboardScreenState.Accepted -> {
                    RideInfo(
                        ride = capture.ride
                    )
                    Text("Picking up ${capture.ride.rider.name.given}")
                }
                is ProviderDashboardScreenState.RiderCanceled -> {
                    Text("RiderCanceled")
                }
                is ProviderDashboardScreenState.InProgress -> {
                    Text("InProgress")
                }
                is ProviderDashboardScreenState.RiderCanceledLate -> {
                    Text("RiderCanceledLate")
                }
                is ProviderDashboardScreenState.DriverCanceled -> {
                    Text("DriverCanceled")
                }
                is ProviderDashboardScreenState.Completed -> {
                    Text("Completed")
                }
            }
        }
    }
}

@Composable
fun RideRequestList(
    requests: List<RideRequestUiState>,
    onReject: (Uuid) -> Unit,
    onAccept: (Uuid) -> Unit,
) {
    Column(
        modifier = Modifier.semantics { contentDescription = "Ride Requests" }
    ) {
        requests.forEach {
            RideRequestView(
                state = it,
                onReject = { onReject(it.requestId) },
                onAccept = { onAccept(it.requestId) },
            )
        }
    }
}

@Composable
fun RideInfo(
    ride: Ride
) {
    Column(
        modifier = Modifier.semantics { contentDescription = "Rider Profile" }
    ) {
        Text(text = ride.rider?.name?.toString() ?: "Rider Name")

        MessageInput(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
) {
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier.semantics { contentDescription = "Message Input" }
    )
}
