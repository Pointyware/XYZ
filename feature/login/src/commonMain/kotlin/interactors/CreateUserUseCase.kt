/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import org.pointyware.xyz.feature.login.data.ProfileRepository

/**
 * Attempts to create a new user.
 */
class CreateUserUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return profileRepository.createUser(email, password).map { }
    }
}
