/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.Profile
import org.pointyware.xyz.feature.login.data.ProfileRepository

/**
 *
 */
class CreateDriverProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: DriverProfile): Result<DriverProfile> {
        return profileRepository.createDriverProfile(profile)
    }
}
