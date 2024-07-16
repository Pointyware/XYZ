/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.interactors

import org.pointyware.xyz.core.entities.Profile
import org.pointyware.xyz.feature.login.data.Authorization
import org.pointyware.xyz.feature.login.data.ProfileRepository

/**
 * Attempts to log in a user.
 */
class LoginUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Authorization> {
        return profileRepository.login(email, password)
    }
}
