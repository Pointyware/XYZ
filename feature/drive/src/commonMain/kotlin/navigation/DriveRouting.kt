/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform
import org.pointyware.xyz.core.navigation.StaticRoute
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.drive.di.DriveDependencies
import org.pointyware.xyz.drive.ui.DriveScreen

val driveRoute = StaticRoute("drive", Unit)

/**
 *
 */
@Composable
fun XyzRootScope.driveRouting(
    dependencies: DriveDependencies,
    navigationDependencies: NavigationDependencies
) {
    location(driveRoute) {
        val driveViewModel = remember { dependencies.getDriveViewModel() }
        val navController = remember { navigationDependencies.getNavController() }

        DriveScreen(
            viewModel = driveViewModel,
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}
