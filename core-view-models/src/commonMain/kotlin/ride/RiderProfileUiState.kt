/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels.ride

import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.viewmodels.ProfileUiState

/**
 *
 */
data class RiderProfileUiState(
    val profile: ProfileUiState,
    val preferences: String,
    val disabilities: Set<Disability>
) {
    companion object {
        val empty = RiderProfileUiState(ProfileUiState.empty, "", emptySet())
    }
}
