/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.Name
import org.pointyware.xyz.core.viewmodels.KoinViewModel

/**
 * Exposes general profile creation functionality.
 */
interface ProfileCreationViewModel {
    val state: StateFlow<ProfileCreationUiState>
    fun onProfileImageSelected(uri: Uri)
    fun onGivenNameChange(name: String)
    fun onMiddleNameChange(name: String)
    fun onFamilyNameChange(name: String)
    fun onBirthdateSelected(date: Instant?)
    fun onGenderSelected(gender: Gender)
}

class ProfileCreationViewModelImpl(

): KoinViewModel(), ProfileCreationViewModel {
    private val mutableState = MutableStateFlow(ProfileCreationUiState.empty)
    override val state: StateFlow<ProfileCreationUiState> get() = mutableState.asStateFlow()

    override fun onProfileImageSelected(uri: Uri) {
        mutableState.update {
            it.copy(image = uri)
        }
    }

    override fun onGivenNameChange(name: String) {
        mutableState.update {
            it.copy(fullName = it.fullName.copy(given = name))
        }
    }

    override fun onMiddleNameChange(name: String) {
        mutableState.update {
            it.copy(fullName = it.fullName.copy(middle = name))
        }
    }

    override fun onFamilyNameChange(name: String) {
        mutableState.update {
            it.copy(fullName = it.fullName.copy(family = name))
        }
    }

    override fun onBirthdateSelected(date: Instant?) {
        mutableState.update {
            it.copy(birthdate = date)
        }
    }

    override fun onGenderSelected(gender: Gender) {
        mutableState.update {
            it.copy(gender = gender)
        }
    }
}

data class ProfileCreationUiState(
    val image: Uri,
    val fullName: Name,
    val birthdate: Instant?,
    val gender: Gender,
) {
    val canSubmit: Boolean
        get() = fullName.given.isNotBlank() &&
                fullName.family.isNotBlank() &&
                birthdate != null

    companion object {
        val empty = ProfileCreationUiState(
            image = Uri.nullDevice,
            fullName = Name("", "", ""),
            birthdate = Clock.System.now(),
            gender = Gender.NotSpecified
        )
    }
}
