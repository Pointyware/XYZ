/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.Profile as ProfileEntity

/**
 * Exposes profile-related actions to be performed by a remote service.
 */
interface ProfileService {
    suspend fun createProfile(userId: Uuid, profile: ProfileEntity): Result<ProfileEntity>
    suspend fun getProfile(userId: Uuid): Result<ProfileEntity>
    suspend fun updateProfile(userId: Uuid, profile: ProfileEntity): Result<ProfileEntity>
    suspend fun deleteProfile(userId: Uuid): Result<Unit>
}

class KtorProfileService(
    val client: HttpClient
): ProfileService {
    override suspend fun createProfile(userId: Uuid, profile: ProfileEntity): Result<ProfileEntity> {
        try {
            val response = client.post(Profile.Id(userId.toString())) {
                setBody(profile)
            }
            return Result.success(response.body())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

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

class TestProfileService(
    private val profiles: MutableMap<Uuid, ProfileEntity> = mutableMapOf()
): ProfileService {

    override suspend fun createProfile(userId: Uuid, profile: ProfileEntity): Result<ProfileEntity> {
        profiles[userId] = profile
        return Result.success(profile)
    }

    override suspend fun getProfile(userId: Uuid): Result<ProfileEntity> {
        profiles[userId]?.let {
            return Result.success(it)
        } ?: run {
            return Result.failure(Exception("Profile not found"))
        }
    }

    override suspend fun updateProfile(userId: Uuid, profile: ProfileEntity): Result<ProfileEntity> {
        profiles[userId] = profile
        return Result.success(profile)
    }

    override suspend fun deleteProfile(userId: Uuid): Result<Unit> {
        profiles.remove(userId)
        return Result.success(Unit)
    }
}
