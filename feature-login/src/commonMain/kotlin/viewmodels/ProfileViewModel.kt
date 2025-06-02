/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.xyz.core.viewmodels.KoinViewModel
import org.pointyware.xyz.core.viewmodels.ProfileUiState
import org.pointyware.xyz.feature.login.data.ProfileRepository

interface ProfileViewModel {
    val state: StateFlow<ProfileUiState>
}

/**
 *
 */
abstract class ProfileViewModelImpl(
    private val profileRepository: ProfileRepository
): KoinViewModel(), ProfileViewModel {

    private val mutableState = MutableStateFlow<ProfileUiState>(ProfileUiState.empty)
    override val state: StateFlow<ProfileUiState>
        get() = mutableState
}
