package org.pointyware.xyz.core.ui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.viewmodels.AuthorizationUiState
import org.pointyware.xyz.core.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.core.viewmodels.LoadingUiState

/**
 * Displays a typical login/registration screen that allows the user to switch between the two.
 * While a submission response is being awaited, the input fields are disabled.
 */
@Composable
fun AuthorizationScreen(
    authorizationViewModel: AuthorizationViewModel,
    modifier: Modifier = Modifier,
) {
    val authorizationState by authorizationViewModel.state.collectAsState()
    val loadingState by authorizationViewModel.loadingState.collectAsState()
    val isEnabled = loadingState == LoadingUiState.Idle // disable input when loading

    Box(
        modifier = modifier,
    ) {
        AuthorizationView(
            state = AuthorizationUiStateMapper.map(authorizationState),
            modifier = Modifier,
            onEmailChange = authorizationViewModel::onEmailChange,
            onPasswordChange = authorizationViewModel::onPasswordChange,
            onPasswordConfirmationChange = authorizationViewModel::onPasswordConfirmationChange,
            onSubmit = authorizationViewModel::onSubmit,
            onSwitch = authorizationViewModel::onSwitch,
        )
        when (val capture = loadingState) {
            LoadingUiState.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize())
            }
            LoadingUiState.Success -> {
                // show success message
                TODO("navigate to next screen")
            }
            is LoadingUiState.Error -> {
                // show error message
                ErrorDialog(
                    state = ErrorState(
                        message = capture.message,
                        dismissLabel = "Dismiss"
                    ),
                    onDismiss = authorizationViewModel::onDismissError
                )
            }
            else -> {
                // show nothing
            }
        }
    }
}

object AuthorizationUiStateMapper {
    fun map(uiState: AuthorizationUiState): AuthorizationViewState {
        return AuthorizationViewState(
            email = uiState.email,
            password = uiState.password,
            passwordConfirmation = uiState.passwordConfirmation,
            isLogin = uiState.isLogin,
        )
    }
}
