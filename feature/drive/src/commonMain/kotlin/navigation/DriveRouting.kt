/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.core.navigation.toTypedKey
import org.pointyware.xyz.drive.di.DriveDependencies
import org.pointyware.xyz.drive.ui.ProviderDashboardScreen
import org.pointyware.xyz.drive.ui.DriverHomeScreen
import org.pointyware.xyz.drive.ui.DriverSettingsScreen

val driverHomeRoute = "drive/home".toTypedKey<Unit>()
val driverActiveRoute = "drive/active".toTypedKey<Unit>()
val driverSettings = "drive/settings".toTypedKey<Unit>()

/**
 *
 */
@Composable
fun XyzRootScope.driveRouting(
    dependencies: DriveDependencies,
    navigationDependencies: NavigationDependencies
) {
    location(driverHomeRoute) {
        val navController = remember { navigationDependencies.getNavController() }
        DriverHomeScreen(navController = navController)
    }
    location(driverActiveRoute) {
        // TODO: replace with viewModel extension function that uses available (Koin)Scope
        val driveViewModel = remember { dependencies.getDriveViewModel() }
        val navController = remember { navigationDependencies.getNavController() }

        ProviderDashboardScreen(
            viewModel = driveViewModel,
            navController = navController
        )
    }
    location(driverSettings) {
        // TODO: replace with viewModel extension function that uses available (Koin)Scope
        val driveViewModel = remember { dependencies.getDriverSettingsViewModel() }

        DriverSettingsScreen(viewModel = driveViewModel)
    }
}
