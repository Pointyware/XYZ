/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.profile.Role
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.navigation.StaticRoute
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.feature.login.DriverProfileCreationScreen
import org.pointyware.xyz.feature.login.RiderProfileCreationScreen
import org.pointyware.xyz.feature.login.di.ProfileDependencies
import org.pointyware.xyz.feature.login.ui.RoleSelectionView
import org.pointyware.xyz.feature.login.ui.UserProfileScreen

private val profileRoute = StaticRoute("profile", Unit)
private val accountCreationRoute = profileRoute.fixed("create")
val roleSelectionRoute = accountCreationRoute.fixed("role")
val driverCreationRoute = accountCreationRoute.fixed("driver")
val riderCreationRoute = accountCreationRoute.fixed("ride")
val userProfileRoute = profileRoute.variable<Uuid>("user-{userId}")
// TODO: replace with TypedKey's

/**
 * Sets up all routes for home navigation.
 */
@Composable
fun XyzRootScope.profileRouting(
    profileDependencies: ProfileDependencies,
    navigationDependencies: NavigationDependencies
) {

    val navController = remember { navigationDependencies.getNavController() }
    companyRouting(
        navController
    )

    location(roleSelectionRoute) {
        RoleSelectionView(
            modifier = Modifier.fillMaxSize(),
            onConfirm = {
                when (it) {
                    Role.Rider -> {
                        navController.navigateTo(riderCreationRoute)
                    }
                    Role.Driver -> {
                        navController.navigateTo(driverCreationRoute)
                    }
                }
            }
        )
    }
    location(driverCreationRoute) {
        val driverProfileViewModel = remember { profileDependencies.getDriverProfileCreationViewModel() }
        DriverProfileCreationScreen(
            viewModel = driverProfileViewModel,
            navController = navController,
        )
    }
    location(riderCreationRoute) {
        val riderProfileViewModel = remember { profileDependencies.getRiderProfileCreationViewModel() }
        RiderProfileCreationScreen(
            viewModel = riderProfileViewModel,
            navController = navController,
        )
    }
    location(userProfileRoute) {
        val profileViewModel = remember { profileDependencies.getProfileViewModel() }
        UserProfileScreen(
            viewModel = profileViewModel,
            navController = navController,
        )
    }
}
