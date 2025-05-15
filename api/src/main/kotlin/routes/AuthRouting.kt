/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
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

/**
 * Passes the result along as a 200 OK response if successful, or a 500 Internal Server Error if not.
 */
private suspend inline fun <reified T : Any> ApplicationCall.respondResult(result: Result<T>) {
    result
        .onSuccess { respond(it) }
        .onFailure { error -> respondText(error.message?.let { error.toString() + it } ?: "Unknown error", status = HttpStatusCode.InternalServerError) }
    respondText("Unreachable", status = HttpStatusCode.InternalServerError)
}
