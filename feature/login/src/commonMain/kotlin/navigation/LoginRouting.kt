/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.navigation.StaticRoute
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.feature.login.di.LoginDependencies
import org.pointyware.xyz.feature.login.ui.AccountCreationScreen
import org.pointyware.xyz.feature.login.ui.AuthorizationScreen

val loginRoute = StaticRoute("login", Unit)
val profileRoute = StaticRoute("profile", Unit)
val accountCreationRoute = profileRoute.fixed("create")

/**
 * Sets up all routes for home navigation.
 */
@Composable
fun XyzRootScope.loginRouting() {
    val di = remember { getKoin() }
    val loginDependencies = remember { di.get<LoginDependencies>() }
    val navigationDependencies = remember { di.get<NavigationDependencies>() }

    location(loginRoute) {
        val authorizationViewModel = remember { loginDependencies.getAuthorizationViewModel() }
        val navController = remember { navigationDependencies.getNavController() }

        AuthorizationScreen(
            authorizationViewModel = authorizationViewModel,
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
    location(accountCreationRoute) {
        AccountCreationScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}
