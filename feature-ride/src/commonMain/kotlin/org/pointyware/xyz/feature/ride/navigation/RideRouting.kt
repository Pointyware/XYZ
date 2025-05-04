/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.core.navigation.toTypedKey
import org.pointyware.xyz.feature.ride.di.RideDependencies
import org.pointyware.xyz.feature.ride.ui.PassengerDashboardScreen

val rideRoute = "ride".toTypedKey<Unit>()

/**
 *
 */
@Composable
fun XyzRootScope.rideRouting(
    dependencies: RideDependencies,
    navigationDependencies: NavigationDependencies
) {
    location(rideRoute) {
        // TODO: replace with viewModel extension function that uses available (Koin)Scope
        val rideViewModel = remember { dependencies.getRideViewModel() }
        val navController = remember { navigationDependencies.getNavController() }

        PassengerDashboardScreen(
            viewModel = rideViewModel,
            navController = navController
        )
    }
}
