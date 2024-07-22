/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.pointyware.xyz.core.entities.profile.Profile
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.feature.login.local.AuthCache
import org.pointyware.xyz.feature.login.local.ProfileCache
import org.pointyware.xyz.feature.login.remote.AuthService
import org.pointyware.xyz.feature.login.remote.ProfileService
import kotlin.coroutines.CoroutineContext

/**
 *
 */
interface ProfileRepository {
    suspend fun createUser(email: String, password: String): Result<Authorization>
    suspend fun createProfile(profile: Profile): Result<Profile>
    suspend fun updateProfile(profile: Profile): Result<Profile>
    suspend fun removeUser(email: String): Result<Unit>
    suspend fun getProfile(email: String): Result<Profile?>
    suspend fun login(email: String, password: String): Result<Login>
}

class ProfileRepositoryImpl(
    private val authCache: AuthCache,
    private val authService: AuthService,
    private val profileCache: ProfileCache,
    private val profileService: ProfileService,
    private val ioContext: CoroutineContext
): ProfileRepository {
    override suspend fun createUser(email: String, password: String): Result<Authorization> {
        return withContext(ioContext) {
            authCache.dropAuth()
            authService.createUser(email, password)
                .onSuccess {
                    authCache.setAuth(it)
                }
        }
    }

    private suspend fun getCurrentUserId(): Uuid {
        return authCache.currentAuth.first()?.userId ?: throw IllegalStateException("currentAuth is null")
    }

    override suspend fun createProfile(profile: Profile): Result<Profile> {
        return withContext(ioContext) {
            try {
                val userId = getCurrentUserId()
                profileService.createProfile(userId, profile)
                    .onSuccess { profileCache.saveProfile(it) }
                    .onFailure { profileCache.dropProfile(userId) }
            } catch(error: Throwable) {
                Result.failure(error)
            }
        }
    }

    override suspend fun updateProfile(profile: Profile): Result<Profile> {
        return withContext(ioContext) {
            try {
                profileService.updateProfile(getCurrentUserId(), profile)
                    .onSuccess { profileCache.saveProfile(it) }
            } catch(error: Throwable) {
                Result.failure(error)
            }
        }
    }

    override suspend fun removeUser(email: String): Result<Unit> {
        return withContext(ioContext) {
            try {
                val userId = getCurrentUserId()
                profileService.deleteProfile(userId)
                    .onSuccess { profileCache.dropProfile(userId) }
            } catch(error: Throwable) {
                Result.failure(error)
            }
        }
    }

    override suspend fun getProfile(email: String): Result<Profile?> {
        return withContext(ioContext) {
            try {
                val userId = getCurrentUserId()
                profileCache.getProfile(userId)
                    .onFailure { profileService.getProfile(userId) }
            } catch(error: Throwable) {
                Result.failure(error)
            }
        }
    }

    override suspend fun login(email: String, password: String): Result<Login> {
        return withContext(ioContext) {
            authService.login(email, password)
                .onSuccess { auth ->
                    authCache.setAuth(auth)
                    profileService.getProfile(auth.userId)
                        .onSuccess { profile ->
                            profile?.let { profileCache.saveProfile(profile) }
                            return@withContext Result.success(Login(auth, profile))
                        }
                        .onFailure { error ->
                            return@withContext Result.failure(error)
                        }
                }
                .onFailure {
                    return@withContext Result.failure(it)
                }
            return@withContext Result.failure(IllegalStateException("This should never happen"))
        }
    }
}
