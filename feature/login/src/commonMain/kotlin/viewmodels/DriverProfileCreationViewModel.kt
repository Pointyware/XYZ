/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.ride.Accommodation
import org.pointyware.xyz.core.viewmodels.KoinViewModel
import org.pointyware.xyz.core.viewmodels.LoadingUiState
import org.pointyware.xyz.core.viewmodels.drive.CompanyProfileUiState
import org.pointyware.xyz.feature.login.interactors.CreateDriverProfileUseCase

interface DriverProfileCreationViewModel {
    val state: StateFlow<DriverProfileCreationUiState>
    val loadingState: StateFlow<LoadingUiState<Unit>>
    val profileCreationViewModel: ProfileCreationViewModel
    fun onAccommodationsSelected(accommodations: List<Accommodation>)
    fun onCompanySelected(uuid: Uuid)
    fun onSubmit()
}

/**
 * A view model for the rider profile creation view.
 */
class DriverProfileCreationViewModelImpl(
    override val profileCreationViewModel: ProfileCreationViewModel,
    private val createProfileUseCase: CreateDriverProfileUseCase
): KoinViewModel(), DriverProfileCreationViewModel {

    private val mutableState = MutableStateFlow(DriverProfileCreationUiState.empty)
    override val state: StateFlow<DriverProfileCreationUiState> get() = mutableState.asStateFlow()
    private val mutableLoadingState = MutableStateFlow<LoadingUiState<Unit>>(LoadingUiState.Idle())
    override val loadingState: StateFlow<LoadingUiState<Unit>> get() = mutableLoadingState.asStateFlow()

    override fun onAccommodationsSelected(accommodations: List<Accommodation>) {
        TODO("Not yet implemented")
    }

    override fun onCompanySelected(uuid: Uuid) {
        TODO("Not yet implemented")
    }

    override fun onSubmit() {
        viewModelScope.launch {
            mutableLoadingState.value = LoadingUiState.Loading()
            createProfileUseCase.invoke(
                TODO("pass profile parameters")
            )
        }
    }
}

/**
 * Extends base [DriverProfileCreationUiState] with driver-specific fields.
 */
data class DriverProfileCreationUiState(
    val profile: ProfileCreationUiState,
    val accommodations: Set<Accommodation> = emptySet(),
    val company: CompanyProfileUiState
) {
    companion object {
        val empty = DriverProfileCreationUiState(
            profile = ProfileCreationUiState.empty,
            company = CompanyProfileUiState.empty
        )
    }
}
