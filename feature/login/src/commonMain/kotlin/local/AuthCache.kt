/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.local

import kotlinx.coroutines.flow.Flow
import org.pointyware.xyz.feature.login.data.Authorization

/**
 * TODO: describe purpose/intent of AuthCache
 */
interface AuthCache {

    val currentAuth: Flow<Authorization?>
    fun setAuth(auth: Authorization)
    fun dropAuth()
}
