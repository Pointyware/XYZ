/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.xyz.core.viewmodels.ViewModel
import org.pointyware.xyz.feature.login.interactors.CreateUserUseCase
import org.pointyware.xyz.feature.login.interactors.LoginUseCase

interface AuthorizationViewModel {
    val loadingState: StateFlow<LoadingUiState>
    val state: StateFlow<AuthorizationUiState>
    fun onEmailChange(email: String)
    fun onPasswordChange(password: String)
    fun onPasswordConfirmationChange(passwordConfirmation: String)
    fun onSubmit()
    fun onSwitch()
    fun onDismissError()
}

/**
 *
 */
class AuthorizationViewModelImpl(
    private val loginUseCase: LoginUseCase,
    private val createUserUseCase: CreateUserUseCase
): ViewModel(), AuthorizationViewModel {

    private val mutableLoadingState = MutableStateFlow<LoadingUiState>(LoadingUiState.Idle)
    override val loadingState: StateFlow<LoadingUiState> get() = mutableLoadingState.asStateFlow()
    private val mutableState = MutableStateFlow(AuthorizationUiState.Empty)
    override val state: StateFlow<AuthorizationUiState> get() = mutableState.asStateFlow()

    override fun onEmailChange(email: String) {
        mutableState.update {
            it.copy(
                email = email,
                isSubmitEnabled = isSubmitOrLoginEnabled(it.isLogin, email, it.password, it.passwordConfirmation)
            )
        }
    }

    override fun onPasswordChange(password: String) {
        mutableState.update {
            it.copy(
                password = password,
                isSubmitEnabled = isSubmitOrLoginEnabled(it.isLogin, it.email, password, it.passwordConfirmation)
            )
        }
    }

    override fun onPasswordConfirmationChange(passwordConfirmation: String) {
        mutableState.update {
            it.copy(
                passwordConfirmation = passwordConfirmation,
                isSubmitEnabled = isSubmitEnabled(it.email, it.password, passwordConfirmation)
            )
        }
    }

    private fun isSubmitOrLoginEnabled(isLogin: Boolean, email: String, password: String, passwordConfirmation: String): Boolean {
        return if (isLogin) { isLoginEnabled(email, password) }
        else { isSubmitEnabled(email, password, passwordConfirmation) }
    }

    private fun isLoginEnabled(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun isSubmitEnabled(email: String, password: String, passwordConfirmation: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && password == passwordConfirmation
    }

    override fun onSubmit() {
        mutableLoadingState.value = LoadingUiState.Loading
        viewModelScope.launch {
            try {
                val state = mutableState.value
                if (state.isLogin) {
                    loginUseCase.invoke(state.email, state.password)
                } else {
                    createUserUseCase.invoke(state.email, state.password)
                }
                mutableLoadingState.value = LoadingUiState.Success
            } catch (e: Exception) {
                mutableLoadingState.value = LoadingUiState.Error(e.message ?: "An error occurred")
            }
        }
    }

    override fun onSwitch() {
        mutableState.update {
            it.copy(isLogin = !it.isLogin)
        }
    }

    override fun onDismissError() {
        mutableLoadingState.value = LoadingUiState.Idle
    }
}

data class AuthorizationUiState(
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val isSubmitEnabled: Boolean = false,
    val isLogin: Boolean = true,
) {
    companion object {
        val Empty = AuthorizationUiState()
    }
}

sealed class LoadingUiState {
    data object Idle : LoadingUiState()
    data object Loading : LoadingUiState()
    data object Success : LoadingUiState()
    data class Error(val message: String) : LoadingUiState()
}
