/*
 * Copyright (c) 2024 Pointyware
 */

package navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.navigation.LocationRootScope
import org.pointyware.xyz.core.navigation.StaticRoute
import org.pointyware.xyz.feature.login.di.LoginDependencies
import org.pointyware.xyz.feature.login.ui.AuthorizationScreen

val loginRoute = StaticRoute("login", Unit)

/**
 * Sets up all routes for home navigation.
 */
@Composable
fun LocationRootScope<Any, Any>.loginRouting() {
    val di = remember { getKoin() }
    val loginDependencies = remember { di.get<LoginDependencies>() }

    location(loginRoute) {
        val authorizationViewModel = remember { loginDependencies.getAuthorizationViewModel() }

        AuthorizationScreen(
            authorizationViewModel = authorizationViewModel,
            modifier = Modifier.fillMaxSize()
        )
    }
}
