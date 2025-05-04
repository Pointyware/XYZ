/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.feature.login.data.ProfileRepository

/**
 * Gets a profile from the repository and checks whether it is a valid driver profile before passing
 * it on.
 */
class GetDriverProfileUseCase(
    private val profileRepo: ProfileRepository
) {
    suspend fun invoke(uuid: String): Result<DriverProfile> {
        return profileRepo.getProfile(uuid)
            .mapCatching { it as DriverProfile }
    }
}
