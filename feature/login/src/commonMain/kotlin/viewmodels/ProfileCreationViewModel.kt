/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.data.Uri
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
}
