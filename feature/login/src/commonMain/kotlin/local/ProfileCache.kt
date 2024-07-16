/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.local

import org.pointyware.xyz.core.entities.Profile

/**
 *
 */
interface ProfileCache {
    fun saveProfile(it: Profile)
    fun dropProfile(email: String)
}

class ProfileCacheImpl(

): ProfileCache {

    private val cache = mutableMapOf<String, Profile>()

    override fun saveProfile(it: Profile) {
        cache[it.email] = it
    }

    override fun dropProfile(email: String) {
        cache.remove(email)
    }
}
