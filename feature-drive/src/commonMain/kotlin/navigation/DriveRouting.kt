/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.drive.ui.DriverSettingsScreen
import org.pointyware.xyz.drive.ui.ProviderDashboardScreen
import org.pointyware.xyz.drive.ui.ProviderHomeScreen
import org.pointyware.xyz.drive.viewmodels.DriverSettingsViewModel
import org.pointyware.xyz.drive.viewmodels.ProviderDashboardViewModel

val driverHomeRoute = "drive/home"
val driverActiveRoute = "drive/active"
val driverSettings = "drive/settings"

/**
 *
 */
fun NavGraphBuilder.driveRouting(
    navController: NavHostController
) {
    composable(driverHomeRoute) {
        ProviderHomeScreen(navController = navController)
    }
    composable(driverActiveRoute) {
        ProviderDashboardScreen(
            viewModel = remember { getKoin().get<ProviderDashboardViewModel>() },
            navController = navController
        )
    }
    composable(driverSettings) {
        DriverSettingsScreen(
            viewModel = remember { getKoin().get<DriverSettingsViewModel>() }
        )
    }
}
