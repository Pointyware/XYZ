/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels.login

import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.profile.Name
import org.pointyware.xyz.core.entities.profile.PhoneNumber

/**
 * Represents the state of a profile creation screen.
 */
data class ProfileCreationUiState(
    val image: Uri,
    val name: Name,
    val phone: PhoneNumber,
    val birthdate: Instant?,
    val gender: Gender
) {
    companion object {
        val empty = ProfileCreationUiState(
            Uri(""),
            Name("", "", ""),
            PhoneNumber(""),
            null,
            Gender.NotSpecified
        )
    }
}
