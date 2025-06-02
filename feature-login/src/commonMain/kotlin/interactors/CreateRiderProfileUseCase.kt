/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import org.pointyware.xyz.core.entities.profile.Profile
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.feature.login.data.ProfileRepository

/**
 *
 */
class CreateRiderProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: RiderProfile): Result<RiderProfile> {
        return profileRepository.createRiderProfile(profile)
    }
}
