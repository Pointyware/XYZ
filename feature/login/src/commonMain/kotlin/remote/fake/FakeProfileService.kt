/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote.fake

import kotlinx.io.files.Path
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.Profile
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.feature.login.remote.ProfileService

/**
 * A test implementation of the [ProfileService] interface. Instead of relying on a remote service,
 * it will load and store profiles in a given directory between test runs.
 */
class FakeProfileService(
    private val profileFile: Path,
    private val profiles: MutableMap<Uuid, Profile> = mutableMapOf()
): ProfileService {

    // TODO: Implement file persistence for testing

    override suspend fun createDriverProfile(userId: Uuid, profile: DriverProfile): Result<DriverProfile> {
        profiles[userId] = profile
        return Result.success(profile)
    }

    override suspend fun createRiderProfile(userId: Uuid, profile: RiderProfile): Result<RiderProfile> {
        profiles[userId] = profile
        return Result.success(profile)
    }

    override suspend fun getProfile(userId: Uuid): Result<Profile?> {
        profiles[userId].let {
            return Result.success(it)
        }
    }

    override suspend fun updateProfile(userId: Uuid, profile: Profile): Result<Profile> {
        profiles[userId] = profile
        return Result.success(profile)
    }

    override suspend fun deleteProfile(userId: Uuid): Result<Unit> {
        profiles.remove(userId)
        return Result.success(Unit)
    }
}
