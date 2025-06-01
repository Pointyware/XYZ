/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.basicAuthProvider
import org.pointyware.xyz.api.controllers.AuthController
import org.pointyware.xyz.api.oauthProvider
import org.pointyware.xyz.api.dtos.LoginInfo
import org.pointyware.xyz.api.dtos.UserSession
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
                val deviceInfo = call.request.headers["User-Agent"] ?: "unknown-device"
                val authController = koin.get<AuthController>()
                call.respondResult(authController.createSession(userId, deviceInfo)
                    .onSuccess {
                        call.sessions.set(UserSession(userId = userId, sessionId = it.toHexString()))
                    })
            }
        }
        post<LoginInfo>("/create") { info ->
            val deviceInfo = call.request.headers["User-Agent"] ?: "unknown-device"
            val authController = koin.get<AuthController>()
            val newId = authController.createUser(info.email, info.password).getOrThrow().toHexString()
            call.respondResult(authController.createSession(uuidString = newId, deviceInfo = deviceInfo)
                .onSuccess {
                    call.sessions.set(UserSession(userId = newId, sessionId = it.toHexString()))
                })
        }
        post("/authorize") {

        }
        post("/token") {

        }
        post("/revoke") {

        }
        authenticate(oauthProvider) {
            get ("/callback") {
                val principal = call.principal<OAuthAccessTokenResponse.OAuth2>()
                principal?.let {
                    principal.state?.let { state ->
                        call.sessions.set(UserSession(state, principal.accessToken)) // TODO: readjust UserSession from SessionId implementation for OAuth
                        // TODO: get redirect URL from a persistent store
//                        redirects[state]?.let { redirect ->
//                            call.respondRedirect(redirect)
//                            return@get
//                        }
                    }
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}
