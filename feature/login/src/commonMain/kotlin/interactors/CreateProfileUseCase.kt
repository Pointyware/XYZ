/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import org.pointyware.xyz.core.entities.profile.Profile
import org.pointyware.xyz.feature.login.data.ProfileRepository

/**
 *
 */
class CreateProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile): Result<Profile> {
        return profileRepository.createProfile(profile)
    }
}
