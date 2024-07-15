package org.pointyware.xyz.feature.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class AuthorizationViewState(
    val isLogin: Boolean = true,
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val isSubmitEnabled: Boolean = false,
)

@Composable
fun AuthorizationView(
    state: AuthorizationViewState,
    modifier: Modifier = Modifier,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onSwitch: () -> Unit,
) {
    Column {
        OutlinedTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = modifier
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            modifier = modifier
        )
        AnimatedVisibility(
            visible = state.isLogin.not()
        ) {
            OutlinedTextField(
                value = state.passwordConfirmation,
                onValueChange = onPasswordConfirmationChange,
                label = { Text("Password Confirmation") },
                modifier = modifier
            )
        }

        Button(
            onClick = onSubmit,
            enabled = state.isSubmitEnabled,
        ) {
            Text("Submit")
        }
        Button(onClick = onSwitch) {
            when (state.isLogin) {
                true -> Text("New Account")
                false -> Text("Existing Account")
            }
        }
    }
}
