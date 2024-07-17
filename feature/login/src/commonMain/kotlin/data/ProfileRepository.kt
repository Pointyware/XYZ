/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.data

import kotlinx.coroutines.withContext
import org.pointyware.xyz.core.entities.Profile
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
    suspend fun getProfile(email: String): Result<Profile>
    suspend fun login(email: String, password: String): Result<Authorization>

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

    override suspend fun createProfile(profile: Profile): Result<Profile> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(profile: Profile): Result<Profile> {
        TODO("Not yet implemented")
    }

    override suspend fun removeUser(email: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(email: String): Result<Profile> {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): Result<Authorization> {
        return authService.login(email, password)
            .onSuccess {
                authCache.setAuth(it)
            }
    }
}
