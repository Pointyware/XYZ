/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.ui.LoadingResultView
import org.pointyware.xyz.core.ui.MapView
import org.pointyware.xyz.core.viewmodels.LoadingUiState
import org.pointyware.xyz.core.viewmodels.MapUiState
import org.pointyware.xyz.feature.ride.entities.PaymentMethod
import org.pointyware.xyz.feature.ride.viewmodels.PassengerDashboardUiState
import org.pointyware.xyz.ui.ads.AdView
import org.pointyware.xyz.ui.ads.AdViewState

data class PassengerDashboardViewState(
    val ride: PassengerDashboardUiState,
    val map: MapUiState
)

/**
 *
 */
@Composable
fun PassengerDashboardView(
    state: PassengerDashboardViewState,
    loadingState: LoadingUiState<Unit>,
    modifier: Modifier = Modifier,
    onStartSearch: ()->Unit,
    onUpdateQuery: (String)->Unit,
    onSendQuery: ()->Unit,
    onSelectLocation: (Location)->Unit,
    onSelectPayment: ()->Unit,
    onPaymentSelected: (PaymentMethod)->Unit,
    onConfirmDetails: ()->Unit,
    onCancel: ()->Unit,
    onCancelTrip: ()->Unit,
    onFinishTrip: ()->Unit,
    onRateDriver: ()->Unit,
    onBack: ()->Unit,
    clearError: ()->Unit
) {
    Box(
        modifier = modifier
    ) {
        MapView(
            state = state.map,
            modifier = modifier.fillMaxSize(),
        )

        AdView(
            state = AdViewState(unit = "ca-app-pub-4288081764974879/8417585021"),
            modifier = Modifier.align(Alignment.TopCenter)
        )

        LoadingResultView(
            state = loadingState,
            onSuccess = {},
            onDismiss = clearError
        )

        TripSearchView(
            state = state.ride,
            modifier = Modifier.align(Alignment.BottomEnd),
            onNewTrip = onStartSearch,
            onUpdateSearch = onUpdateQuery,
            onSendQuery = onSendQuery,
            onSelectLocation = onSelectLocation,
            onConfirmDetails = onConfirmDetails,
            onSelectPayment = onSelectPayment,
            onPaymentSelected = onPaymentSelected,
            onCancelRequest = onCancel,
            onCancelTrip = onCancelTrip,
            onFinishTrip = onFinishTrip,
            onRateDriver = onRateDriver,
        )
    }
}
