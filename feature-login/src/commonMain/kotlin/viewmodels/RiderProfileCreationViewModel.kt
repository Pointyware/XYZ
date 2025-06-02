/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.core.viewmodels.KoinViewModel
import org.pointyware.xyz.core.viewmodels.LoadingUiState
import org.pointyware.xyz.core.viewmodels.postError
import org.pointyware.xyz.feature.login.interactors.CreateRiderProfileUseCase
import org.pointyware.xyz.feature.login.interactors.GetUserIdUseCase
import kotlin.uuid.ExperimentalUuidApi

interface RiderProfileCreationViewModel {
    val state: StateFlow<RiderProfileCreationUiState>
    val loadingState: StateFlow<LoadingUiState<Unit>>
    val profileCreationViewModel: ProfileCreationViewModel
    fun onDisabilitiesSelected(disabilities: List<Disability>)
    fun onPreferencesChange(preferences: String)
    fun clearLoadingError()
    fun onSubmit()
}

/**
 * A view model for the rider profile creation view.
 */
@OptIn(ExperimentalUuidApi::class)
class RiderProfileCreationViewModelImpl(
    override val profileCreationViewModel: ProfileCreationViewModel,
    private val createProfileUseCase: CreateRiderProfileUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
): KoinViewModel(), RiderProfileCreationViewModel {

    private val mutableState = MutableStateFlow(RiderProfileCreationUiState.empty)
    override val state: StateFlow<RiderProfileCreationUiState> get() = mutableState.asStateFlow()
    private val mutableLoadingState = MutableStateFlow<LoadingUiState<Unit>>(LoadingUiState.Idle())
    override val loadingState: StateFlow<LoadingUiState<Unit>> get() = mutableLoadingState.asStateFlow()

    init {
        viewModelScope.launch {
            profileCreationViewModel.state.collect { profileState ->
                mutableState.update {
                    it.copy(profile = profileState)
                }
            }
        }
    }

    override fun onDisabilitiesSelected(disabilities: List<Disability>) {
        mutableState.update {
            it.copy(
                disabilities = disabilities
            )
        }
    }

    override fun onPreferencesChange(preferences: String) {
        mutableState.update {
            it.copy(
                preferences = preferences
            )
        }
    }

    override fun clearLoadingError() {
        mutableLoadingState.value = LoadingUiState.Idle()
    }

    override fun onSubmit() {
        viewModelScope.launch {
            mutableLoadingState.value = LoadingUiState.Loading()
            getUserIdUseCase.invoke()
                .onSuccess { id ->
                    val state = state.value
                    val riderProfile = RiderProfile(
                        id = id,
                        name = state.profile.fullName,
                        preferences = state.preferences,
                        disabilities = state.disabilities.toSet(),
                        gender = state.profile.gender,
                        picture = state.profile.image,
                    )
                    createProfileUseCase.invoke(riderProfile)
                        .onSuccess {
                            mutableLoadingState.value = LoadingUiState.Success(Unit)
                        }
                        .onFailure {
                            mutableLoadingState.postError(it)
                        }
                }
                .onFailure { error ->
                    mutableLoadingState.postError(error)
                }
        }
    }
}

/**
 * Extends base [RiderProfileCreationUiState] with driver-specific fields.
 */
data class RiderProfileCreationUiState(
    val profile: ProfileCreationUiState,
    val disabilities: List<Disability>,
    val preferences: String
) {

    val canSubmit: Boolean
        get() = profile.canSubmit

    companion object {
        val empty = RiderProfileCreationUiState(
            profile = ProfileCreationUiState.empty,
            disabilities = emptyList(),
            preferences = ""
        )
    }
}
