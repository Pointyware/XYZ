/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.viewmodels.KoinViewModel
import org.pointyware.xyz.core.viewmodels.ProfileUiState

/**
 * Exposes general profile creation functionality.
 */
interface ProfileCreationViewModel<out P:ProfileUiState> {
    val state: StateFlow<P>
    fun onProfileImageSelected(uri: Uri)
    fun onGivenNameChange(name: String)
    fun onMiddleNameChange(name: String)
    fun onFamilyNameChange(name: String)
    fun onBirthdateSelected(date: Instant?)
    fun onGenderSelected(gender: Gender)
    fun onSubmit()
}

abstract class ProfileCreationViewModelImpl<P:ProfileUiState>(
    initialState: P
): KoinViewModel(), ProfileCreationViewModel<P> {
    protected val mutableState = MutableStateFlow(initialState)
    override val state: StateFlow<P> get() = mutableState.asStateFlow()

    override fun onProfileImageSelected(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun onGivenNameChange(name: String) {
        TODO("Not yet implemented")
    }

    override fun onMiddleNameChange(name: String) {
        TODO("Not yet implemented")
    }

    override fun onFamilyNameChange(name: String) {
        TODO("Not yet implemented")
    }

    override fun onBirthdateSelected(date: Instant?) {
        TODO("Not yet implemented")
    }

    override fun onGenderSelected(gender: Gender) {
        TODO("Not yet implemented")
    }
}
