/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.Name
import org.pointyware.xyz.core.entities.ride.Rating

/**
 * A detailed profile UI state. For less detail see [BriefProfileUiState].
 * Excludes email or any sensitive information.
 */
data class ProfileUiState(
    val image: Uri,
    val fullName: Name,
    val gender: Gender,
    val age: Int,
    val bio: String,
    val rating: Rating,
) {
    companion object {
        val empty = ProfileUiState(
            image = Uri.nullDevice,
            fullName = Name("", "", ""),
            gender = Gender.NotSpecified,
            age = 0,
            bio = "",
            rating = Rating.FIVE
        )
    }
}
