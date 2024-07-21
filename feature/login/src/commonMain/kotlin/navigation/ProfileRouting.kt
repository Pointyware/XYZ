/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.navigation.StaticRoute
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.feature.login.di.LoginDependencies
import org.pointyware.xyz.feature.login.di.ProfileDependencies
import org.pointyware.xyz.feature.login.ui.AccountCreationScreen
import org.pointyware.xyz.feature.login.ui.UserProfileScreen

val profileRoute = StaticRoute("profile", Unit)
val accountCreationRoute = profileRoute.fixed("create")
val userProfileRoute = profileRoute.variable<Uuid>("userId")

/**
 * Sets up all routes for home navigation.
 */
@Composable
fun XyzRootScope.profileRouting(
    profileDependencies: ProfileDependencies,
    navigationDependencies: NavigationDependencies
) {

    location(accountCreationRoute) {
        val accountCreationViewModel = remember { profileDependencies.getAccountCreationViewModel() }
        val navController = remember { navigationDependencies.getNavController() }

        AccountCreationScreen(
            viewModel = accountCreationViewModel,
            navController = navController,
        )
    }
    location(userProfileRoute) {
        val profileViewModel = remember { profileDependencies.getProfileViewModel() }
        val navController = remember { navigationDependencies.getNavController() }

        UserProfileScreen(
            viewModel = profileViewModel,
            navController = navController,
        )
    }
}
