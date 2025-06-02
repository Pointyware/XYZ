/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.pointyware.xyz.api.dtos.Authorization

/**
 * This cache stores the current authorization state of the user. It should only maintain data relevant to authentication/authorization.
 */
interface AuthCache {

    val currentAuth: Flow<Authorization?>
    fun setAuth(auth: Authorization)
    fun dropAuth()
}

class AuthCacheImpl: AuthCache {

    private val mutableCurrentAuth = MutableStateFlow<Authorization?>(null)
    override val currentAuth: Flow<Authorization?>
        get() = mutableCurrentAuth

    override fun setAuth(auth: Authorization) {
        mutableCurrentAuth.value = auth
    }

    override fun dropAuth() {
        mutableCurrentAuth.value = null
    }
}
