/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import org.pointyware.xyz.core.ui.LoadingResultView
import org.pointyware.xyz.drive.navigation.driverHomeRoute
import org.pointyware.xyz.feature.login.navigation.roleSelectionRoute
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationEvent
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationUiState
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.ride.navigation.rideRoute

/**
 * Displays a typical login/registration screen that allows the user to switch between the two.
 * While a submission response is being awaited, the input fields are disabled.
 */
@Composable
fun AuthorizationScreen(
    authorizationViewModel: AuthorizationViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val authorizationState by authorizationViewModel.state.collectAsState()
    val loadingState by authorizationViewModel.loadingState.collectAsState()

    Box(
        modifier = modifier,
    ) {
        AuthorizationView(
            state = AuthorizationUiStateMapper.map(authorizationState),
            modifier = Modifier.align(Alignment.Center),
            onEmailChange = authorizationViewModel::onEmailChange,
            onPasswordChange = authorizationViewModel::onPasswordChange,
            onPasswordConfirmationChange = authorizationViewModel::onPasswordConfirmationChange,
            onSubmit = authorizationViewModel::onSubmit,
            onSwitch = authorizationViewModel::onSwitch,
        )
        LoadingResultView(
            state = loadingState,
            onSuccess = {
                val event = it
                val location = when (event) {
                    AuthorizationEvent.NewUser -> {
                        roleSelectionRoute
                    }
                    AuthorizationEvent.Ride -> {
                        rideRoute
                    }
                    AuthorizationEvent.Driver -> {
                        driverHomeRoute
                    }
                }
                navController.navigate(location, navOptions = navOptions { launchSingleTop = true })
            },
            onDismiss = authorizationViewModel::onDismissError
        )
    }
}

object AuthorizationUiStateMapper {
    fun map(uiState: AuthorizationUiState): AuthorizationViewState {
        return AuthorizationViewState(
            email = uiState.email,
            password = uiState.password,
            passwordConfirmation = uiState.passwordConfirmation,
            isSubmitEnabled = uiState.isSubmitEnabled,
            isLogin = uiState.isLogin,
        )
    }
}
