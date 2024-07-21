/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.Role
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.navigation.StaticRoute
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.drive.ui.DriverProfileCreationView
import org.pointyware.xyz.feature.login.di.ProfileDependencies
import org.pointyware.xyz.feature.login.ui.RoleSelectionView
import org.pointyware.xyz.feature.login.ui.UserProfileScreen
import org.pointyware.xyz.feature.ride.ui.RiderProfileCreationView

val profileRoute = StaticRoute("profile", Unit)
val accountCreationRoute = profileRoute.fixed("create")
val roleSelectionRoute = accountCreationRoute.fixed("role")
val driverCreationRoute = accountCreationRoute.fixed("driver")
val riderCreationRoute = accountCreationRoute.fixed("rider")
val userProfileRoute = profileRoute.variable<Uuid>("user-{userId}")

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
        DriverProfileCreationView(
            state = TODO(),
            modifier = Modifier.fillMaxSize(),
            onProfileImageSelected = TODO(),
            onGivenNameChange = TODO(),
            onMiddleNameChange = TODO(),
            onFamilyNameChange = TODO(),
            onBirthdateSelected = TODO(),
            onGenderSelected = TODO(),
            onCompanySelected = TODO(),
            onSubmit = TODO(),
        )
    }
    location(riderCreationRoute) {
        RiderProfileCreationView(
            state = TODO(),
            modifier = Modifier.fillMaxSize(),
            onProfileImageSelected = TODO(),
            onGivenNameChange = TODO(),
            onMiddleNameChange = TODO(),
            onFamilyNameChange = TODO(),
            onBirthdateSelected = TODO(),
            onGenderSelected = TODO(),
            onDisabilitiesSelected = TODO(),
            onPreferencesChange = TODO(),
            onSubmit = TODO(),
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
