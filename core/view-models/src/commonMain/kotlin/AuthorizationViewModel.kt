package org.pointyware.xyz.core.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
    /*
    Interactors:
    - RegisterUser
    - LoginUser
     */
): AuthorizationViewModel {

    private val mutableLoadingState = MutableStateFlow(LoadingUiState.Idle)
    override val loadingState: StateFlow<LoadingUiState> get() = mutableLoadingState.asStateFlow()
    private val mutableState = MutableStateFlow(AuthorizationUiState.Empty)
    override val state: StateFlow<AuthorizationUiState> get() = mutableState.asStateFlow()

    override fun onEmailChange(email: String) {
        TODO("Not yet implemented")
    }

    override fun onPasswordChange(password: String) {
        TODO("Not yet implemented")
    }

    override fun onPasswordConfirmationChange(passwordConfirmation: String) {
        TODO("Not yet implemented")
    }

    override fun onSubmit() {
        TODO("Not yet implemented")
    }

    override fun onSwitch() {
        TODO("Not yet implemented")
    }

    override fun onDismissError() {
        mutableLoadingState.value = LoadingUiState.Idle
    }
}

data class AuthorizationUiState(
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
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
