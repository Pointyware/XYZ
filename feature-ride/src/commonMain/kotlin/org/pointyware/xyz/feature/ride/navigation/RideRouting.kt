/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.pointyware.xyz.feature.ride.ui.PassengerDashboardScreen
import org.pointyware.xyz.feature.ride.viewmodels.TripViewModel

val rideRoute = "ride"

/**
 *
 */
fun NavGraphBuilder.rideRouting(
    navController: NavHostController,
) {
    composable(rideRoute) {
        // TODO: replace with viewModel extension function that uses available (Koin)Scope
        val rideViewModel: TripViewModel = TODO()

        PassengerDashboardScreen(
            viewModel = rideViewModel,
            navController = navController
        )
    }
}
