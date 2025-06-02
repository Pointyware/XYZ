/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.feature.login.ui.AuthorizationScreen
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel

val loginRoute = "login"

/**
 * Sets up all routes for home navigation.
 */
fun NavGraphBuilder.loginRouting(
    navController: NavHostController,
) {

    composable(loginRoute) {
        val authorizationViewModel = remember {  getKoin().get<AuthorizationViewModel>()  }

        AuthorizationScreen(
            authorizationViewModel = authorizationViewModel,
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}
