/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.data

import org.pointyware.xyz.core.entities.Profile
import org.pointyware.xyz.feature.login.local.ProfileCache
import org.pointyware.xyz.feature.login.remote.ProfileService

/**
 *
 */
interface ProfileRepository {
    suspend fun createUser(email: String, password: String): Result<Profile>
    suspend fun createProfile(profile: Profile): Result<Profile>
    suspend fun updateProfile(profile: Profile): Result<Profile>

    suspend fun login(email: String, password: String): Result<Profile>

}

class ProfileRepositoryImpl(
    private val profileCache: ProfileCache,
    private val profileService: ProfileService
): ProfileRepository {
    override suspend fun createUser(email: String, password: String): Result<Profile> {
        TODO("Not yet implemented")
    }

    override suspend fun createProfile(profile: Profile): Result<Profile> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(profile: Profile): Result<Profile> {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): Result<Profile> {
        return profileService.login(email, password)
            .onSuccess {
                profileCache.saveProfile(it)
            }
            .onFailure {
                profileCache.dropProfile(email)
            }
    }
}
