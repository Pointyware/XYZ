/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels.drive

import org.pointyware.xyz.core.entities.ride.Accommodation
import org.pointyware.xyz.core.viewmodels.ProfileUiState

/**
 * The state of a driver's profile.
 */
data class DriverProfileUiState(
    val profile: ProfileUiState,
    val accommodations: Set<Accommodation>,
    val company: CompanyProfileUiState,
) {
    companion object {
        val Empty = DriverProfileUiState(
            ProfileUiState.empty,
            emptySet(),
            CompanyProfileUiState.empty
        )
    }
}
