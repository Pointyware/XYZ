/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.interactors

import kotlinx.coroutines.flow.first
import kotlin.uuid.Uuid
import org.pointyware.xyz.feature.login.local.AuthCache
import kotlin.uuid.ExperimentalUuidApi

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class GetUserIdUseCase(
    private val authCache: AuthCache
) {

    suspend operator fun invoke(): Result<Uuid> {
        return authCache.currentAuth.first()?.let {
            Result.success(it.userId)
        } ?: Result.failure(IllegalStateException("User not logged in"))
    }
}
