/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.resources.Resource

/**
 *
 */
@Resource("auth")
sealed interface Auth {
    @Resource("login")
    object Login
    @Resource("create")
    object Create
}
