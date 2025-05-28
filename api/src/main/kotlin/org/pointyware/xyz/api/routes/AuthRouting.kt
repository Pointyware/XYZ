/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.basicAuthProvider
import org.pointyware.xyz.api.controllers.AuthController
import org.pointyware.xyz.core.data.dtos.LoginInfo
import org.pointyware.xyz.core.data.dtos.UserSession
import kotlin.uuid.ExperimentalUuidApi

/**
 * Directs auth requests to the appropriate controller.
 *
 * For now, we only have one app to authenticate against, so we don't need a dedicated auth server.
 *
 * In the future, we will have a dedicated auth server under the subdomain `auth.pointyware.org`.
 */
@OptIn(ExperimentalUuidApi::class)
fun Routing.auth() {
    val koin = getKoin()
    route("/auth") {
        authenticate(basicAuthProvider) {
            post("/login") {
                val userId = call.principal<UserIdPrincipal>()!!.name
                // TODO: get device info from request headers or session
                val deviceInfo = call.request.headers["User-Agent"] ?: "unknown-device"
                val authController = koin.get<AuthController>()
                val result = authController.createSession(userId, deviceInfo)
                    .onSuccess {
                        call.sessions.set(UserSession(userId = userId, sessionId = it.toHexString()))
                    }

                call.respondResult(result)
            }
        }
        authenticate(basicAuthProvider) {
            post<LoginInfo>("/create") { info ->
                val authController = koin.get<AuthController>()

                call.respondResult(authController.createUser(info.email, info.password))
            }
        }
    }
}
