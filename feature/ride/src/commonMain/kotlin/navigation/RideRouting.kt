/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform
import org.pointyware.xyz.core.navigation.StaticRoute
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.feature.ride.di.RideDependencies
import org.pointyware.xyz.feature.ride.ui.RideScreen

val rideRoute = StaticRoute("ride", Unit)

/**
 *
 */
@Composable
fun XyzRootScope.rideRouting(
    dependencies: RideDependencies,
    navigationDependencies: NavigationDependencies
) {
    location(rideRoute) {
        val rideViewModel = remember { dependencies.getRideViewModel() }
        val navController = remember { navigationDependencies.getNavController() }

        RideScreen(
            viewModel = rideViewModel,
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}
