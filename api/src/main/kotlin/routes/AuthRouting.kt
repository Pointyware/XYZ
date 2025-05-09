/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respondNullable
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.controllers.AuthController
import org.pointyware.xyz.core.data.dtos.LoginInfo

/**
 * Directs auth requests to the appropriate controller.
 */
fun Routing.auth() {
    val koin = getKoin()
    post<LoginInfo>("/auth/login") { info ->
        val authService = koin.get<AuthController>()

        authService.login(info.email, info.password)
            .onSuccess { call.respondNullable(it) }
            .onFailure { call.respondText(it.message ?: "Unknown error") }
        call.respondText("Unreachable", status = HttpStatusCode.InternalServerError)
    }
    post<LoginInfo>("/auth/create") { info ->
        val authService = koin.get<AuthController>()

        authService.createUser(info.email, info.password)
            .onSuccess { call.respondNullable(it) }
            .onFailure { call.respondText(it.message ?: "Unknown error") }
        call.respondText("Unreachable", status = HttpStatusCode.InternalServerError)
    }
}
