/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.request.setBody
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.Profile as ProfileEntity

/**
 * Exposes profile-related actions to be performed by a remote service.
 */
interface ProfileService {
    suspend fun getProfile(userId: Uuid): Result<ProfileEntity>
    suspend fun updateProfile(userId: Uuid, profile: ProfileEntity): Result<ProfileEntity>
    suspend fun deleteProfile(userId: Uuid): Result<Unit>
}

class KtorProfileService(
    val client: HttpClient
): ProfileService {
    override suspend fun getProfile(userId: Uuid): Result<ProfileEntity> {
        try {
            val response = client.get(Profile.Id(userId.toString()))
            return Result.success(response.body<ProfileEntity>())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun updateProfile(userId: Uuid, profile: ProfileEntity): Result<ProfileEntity> {
        try {
            val response = client.patch(Profile.Id(userId.toString())) {
                setBody(profile)
            }
            return Result.success(response.body<ProfileEntity>())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun deleteProfile(userId: Uuid): Result<Unit> {
        try {
            client.delete(Profile.Id(userId.toString()))
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
