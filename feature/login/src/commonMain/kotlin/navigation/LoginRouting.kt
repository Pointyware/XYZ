/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.core.navigation.toTypedKey
import org.pointyware.xyz.feature.login.di.LoginDependencies
import org.pointyware.xyz.feature.login.ui.AuthorizationScreen

val loginRoute = "login".toTypedKey<Unit>()

/**
 * Sets up all routes for home navigation.
 */
@Composable
fun XyzRootScope.loginRouting(
    dependencies: LoginDependencies,
    navigationDependencies: NavigationDependencies
) {

    location(loginRoute) {
        // TODO: replace with viewModel extension function that uses available (Koin)Scope
        val authorizationViewModel = remember { dependencies.getAuthorizationViewModel() }
        val navController = remember { navigationDependencies.getNavController() }

        AuthorizationScreen(
            authorizationViewModel = authorizationViewModel,
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}
