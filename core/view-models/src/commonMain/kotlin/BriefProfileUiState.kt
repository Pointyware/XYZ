/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.ride.Rating
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.profile.Profile

/**
 * A brief profile UI state. For more detail see [ProfileUiState].
 */
interface BriefProfileUiState {
    val id: Uuid
    val image: Uri
    val name: String
    val rating: Rating
}

data class BriefProfileUiStateWrapper(
    private val profile: Profile
): BriefProfileUiState {
    override val id: Uuid = profile.id
    override val image: Uri = profile.picture
    override val name: String = profile.name.given
    override val rating: Rating = Rating.FIVE // TODO: get from profile
}

fun Profile.toBriefProfileUiState(): BriefProfileUiState = BriefProfileUiStateWrapper(this)
