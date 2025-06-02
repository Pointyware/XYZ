/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation

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
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            singleLine = true,
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
        )
        AnimatedVisibility(
            visible = state.isLogin.not()
        ) {
            OutlinedTextField(
                value = state.passwordConfirmation,
                onValueChange = onPasswordConfirmationChange,
                label = { Text("Password Confirmation") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
            )
        }

        Button(
            onClick = onSubmit,
            enabled = state.isSubmitEnabled,
        ) {
            when (state.isLogin) {
                true -> Text("Login")
                false -> Text("Submit")
            }
        }
        Button(onClick = onSwitch) {
            when (state.isLogin) {
                true -> Text("New Account")
                false -> Text("Existing Account")
            }
        }
    }
}
