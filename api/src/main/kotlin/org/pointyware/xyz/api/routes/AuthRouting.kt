/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.controllers.AuthController
import org.pointyware.xyz.core.data.dtos.LoginInfo

/**
 * Directs auth requests to the appropriate controller.
 *
 * For now, we only have one app to authenticate against, so we don't need a dedicated auth server.
 *
 * In the future, we will have a dedicated auth server under the subdomain `auth.pointyware.org`.
 */
fun Routing.auth() {
    val koin = getKoin()
    route("/auth") {
        post<LoginInfo>("/login") { info ->
            val authController = koin.get<AuthController>()

            call.respondResult(authController.login(info.email, info.password))
        }
        post<LoginInfo>("/create") { info ->
            val authController = koin.get<AuthController>()

            call.respondResult(authController.createUser(info.email, info.password))
        }
    }
}
