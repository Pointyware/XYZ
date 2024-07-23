/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import kotlinx.coroutines.flow.first
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.feature.login.local.AuthCache

/**
 *
 */
class GetUserIdUseCase(
    private val authCache: AuthCache
) {

    suspend operator fun invoke(): Result<Uuid> {
        return authCache.currentAuth.first()?.let {
            Result.success(it.userId)
        } ?: Result.failure(IllegalStateException("User not logged in"))
    }
}
