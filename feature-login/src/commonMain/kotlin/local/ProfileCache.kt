/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.local

import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.profile.Profile
import kotlin.uuid.ExperimentalUuidApi

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
interface ProfileCache {
    fun saveProfile(it: Profile)
    fun getProfile(userId: Uuid): Result<Profile?>
    fun dropProfile(userId: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
class ProfileCacheImpl(

): ProfileCache {

    private val cache = mutableMapOf<Uuid, Profile>()

    override fun saveProfile(it: Profile) {
        cache[it.id] = it
    }

    override fun getProfile(userId: Uuid): Result<Profile?> {
        return cache[userId].let { Result.success(it) }
    }

    override fun dropProfile(userId: Uuid) {
        cache.remove(userId)
    }
}
