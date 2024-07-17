/*
 * Copyright (c) 2024 Pointyware
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
