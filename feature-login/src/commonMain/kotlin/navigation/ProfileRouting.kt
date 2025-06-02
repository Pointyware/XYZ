/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.entities.profile.Role
import org.pointyware.xyz.feature.login.DriverProfileCreationScreen
import org.pointyware.xyz.feature.login.RiderProfileCreationScreen
import org.pointyware.xyz.feature.login.ui.RoleSelectionView
import org.pointyware.xyz.feature.login.ui.UserProfileScreen
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.ProfileViewModel
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel

val roleSelectionRoute = "profile/create/role"
val driverCreationRoute = "profile/create/driver"
val riderCreationRoute = "profile/create/ride"
val userProfileRoute = "profile/{userId}"

/**
 * Sets up all routes for home navigation.
 */
fun NavGraphBuilder.profileRouting(
    navController: NavHostController,
) {
    companyRouting(
        navController
    )

    composable(roleSelectionRoute) {
        RoleSelectionView(
            modifier = Modifier.fillMaxSize(),
            onConfirm = {
                when (it) {
                    Role.Rider -> {
                        navController.navigate(riderCreationRoute)
                    }
                    Role.Driver -> {
                        navController.navigate(driverCreationRoute)
                    }
                }
            }
        )
    }
    composable(driverCreationRoute) {
        val driverProfileViewModel = remember { getKoin().get<DriverProfileCreationViewModel>() }
        DriverProfileCreationScreen(
            viewModel = driverProfileViewModel,
            navController = navController,
        )
    }
    composable(riderCreationRoute) {
        val riderProfileViewModel = remember { getKoin().get<RiderProfileCreationViewModel>() }
        RiderProfileCreationScreen(
            viewModel = riderProfileViewModel,
            navController = navController,
        )
    }
    composable(userProfileRoute) {
        val profileViewModel = remember { getKoin().get<ProfileViewModel>() }
        UserProfileScreen(
            viewModel = profileViewModel,
            navController = navController,
        )
    }
}
