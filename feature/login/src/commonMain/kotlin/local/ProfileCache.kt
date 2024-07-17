/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
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
