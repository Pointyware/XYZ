/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.resources.Resource

@Resource("/auth")
class Auth {
    @Resource("login")
    class Login(val parent: Auth = Auth())
    @Resource("create")
    class Create(val parent: Auth = Auth())
}
