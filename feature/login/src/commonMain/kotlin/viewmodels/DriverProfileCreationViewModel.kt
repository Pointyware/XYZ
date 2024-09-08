/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.ride.Accommodation
import org.pointyware.xyz.core.viewmodels.KoinViewModel
import org.pointyware.xyz.core.viewmodels.LoadingUiState
import org.pointyware.xyz.core.viewmodels.drive.AccommodationsSelectionUiState
import org.pointyware.xyz.core.viewmodels.drive.BriefCompanyProfileUiState
import org.pointyware.xyz.core.viewmodels.drive.CompanySelectionUiState
import org.pointyware.xyz.core.viewmodels.postError
import org.pointyware.xyz.feature.login.interactors.CreateDriverProfileUseCase
import org.pointyware.xyz.feature.login.interactors.GetCompanyUseCase

interface DriverProfileCreationViewModel {
    val state: StateFlow<DriverProfileCreationUiState>
    val loadingState: StateFlow<LoadingUiState<Unit>>
    val profileCreationViewModel: ProfileCreationViewModel
    fun onAccommodationsSelected(accommodations: List<Accommodation>)
    fun onCompanySelected(uuid: Uuid)
    fun onSubmit()
    fun onCompanySearchChange(query: String)
}

/**
 * A view model for the rider profile creation view.
 */
class DriverProfileCreationViewModelImpl(
    override val profileCreationViewModel: ProfileCreationViewModel,
    private val createProfileUseCase: CreateDriverProfileUseCase,
    private val getCompanyProfileUseCase: GetCompanyUseCase,
): KoinViewModel(), DriverProfileCreationViewModel {

    private val mutableState = MutableStateFlow(DriverProfileCreationUiState.empty)
    override val state: StateFlow<DriverProfileCreationUiState> get() = mutableState.asStateFlow()
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

    override fun onAccommodationsSelected(accommodations: List<Accommodation>) {
        mutableState.update {
            it.copy(accommodations = it.accommodations.copy(selected = accommodations))
        }
    }

    override fun onCompanySearchChange(query: String) {
        TODO("Not yet implemented")
    }



    private var companySelectionJob: Job? = null
    override fun onCompanySelected(uuid: Uuid) {
        companySelectionJob?.cancel()
        companySelectionJob = viewModelScope.launch {
            getCompanyProfileUseCase.invoke(uuid)
                .onSuccess {
                    val companyProfileUiState = BriefCompanyProfileUiState(
                        id = uuid,
                        logo = it.logo,
                        name = it.name,
                    )
                    mutableState.update {
                        it.copy(companySelection =
                        CompanySelectionUiState(
                            search = "",
                            suggestions = emptyList(),
                            selected = companyProfileUiState
                        )
                        )
                    }
                }
                .onFailure {
                    mutableLoadingState.postError(it)
                }
        }
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
    val accommodations: AccommodationsSelectionUiState,
    val companySelection: CompanySelectionUiState,
) {

    val canSubmit: Boolean
        get() = profile.canSubmit && companySelection.selected != null

    companion object {
        val empty = DriverProfileCreationUiState(
            profile = ProfileCreationUiState.empty,
            accommodations = AccommodationsSelectionUiState.empty,
            companySelection = CompanySelectionUiState.empty
        )
    }
}
