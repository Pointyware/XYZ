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
        profileRepo.getProfile(uuid)
            .onSuccess { profile ->
                return when (profile) {
                    is DriverProfile -> Result.success(profile)
                    else -> Result.failure(Exception("Profile is not a DriverProfile"))
                }
            }
            .onFailure { return Result.failure(it) }
        return Result.failure(IllegalStateException("This should never happen"))
    }
}
