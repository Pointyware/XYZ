/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.feature.ride.viewmodels.TripViewModel

/**
 * Displays a map with controls for starting, monitoring, and canceling a ride.
 */
@Composable
fun PassengerDashboardScreen(
    viewModel: TripViewModel,
    navController: XyzNavController,
) {
    val state = viewModel.state.collectAsState()
    val mapState = viewModel.mapState.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()

    val rideViewState = RideUiStateMapper.map(state.value to mapState.value)
    PassengerDashboardView(
        state = rideViewState,
        loadingState = loadingState.value,
        modifier = Modifier.fillMaxSize(),
        onStartSearch = { viewModel.startSearch() },
        onUpdateQuery = { viewModel.updateQuery(it) },
        onSendQuery = { viewModel.sendQuery() },
        onSelectLocation = { viewModel.selectLocation(it) },
        onConfirmDetails = { viewModel.confirmDetails() },
        onCancel = { viewModel.cancelRide() },
        onBack = { navController.goBack() },
        clearError = { viewModel.clearError() },
        onSelectPayment = viewModel::onSelectPayment,
        onPaymentSelected = viewModel::onPaymentSelected
    )
}
