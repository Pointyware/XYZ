/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.RiderProfile
import kotlin.uuid.ExperimentalUuidApi
import org.pointyware.xyz.core.entities.profile.Profile as ProfileEntity

/**
 * Exposes profile-related actions to be performed by a remote service.
 */
@OptIn(ExperimentalUuidApi::class)
interface ProfileService {
    suspend fun createDriverProfile(userId: Uuid, profile: DriverProfile): Result<DriverProfile>
    suspend fun createRiderProfile(userId: Uuid, profile: RiderProfile): Result<RiderProfile>
    suspend fun getProfile(userId: Uuid): Result<ProfileEntity?>
    suspend fun updateProfile(userId: Uuid, profile: ProfileEntity): Result<ProfileEntity>
    suspend fun deleteProfile(userId: Uuid): Result<Unit>
}

@OptIn(ExperimentalUuidApi::class)
class KtorProfileService(
    val client: HttpClient
): ProfileService {

    override suspend fun createDriverProfile(
        userId: Uuid,
        profile: DriverProfile
    ): Result<DriverProfile> {
        try {
            val response = client.post(Profile.Id(userId.toString())) {
                setBody(profile)
            }
            return Result.success(response.body())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun createRiderProfile(
        userId: Uuid,
        profile: RiderProfile
    ): Result<RiderProfile> {
        try {
            val response = client.post(Profile.Id(userId.toString())) {
                setBody(profile)
            }
            return Result.success(response.body())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getProfile(userId: Uuid): Result<ProfileEntity?> {
        try {
            val response = client.get(Profile.Id(userId.toString()))
            return Result.success(response.body<ProfileEntity?>())
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
