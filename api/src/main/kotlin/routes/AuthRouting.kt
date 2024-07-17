/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

/**
 * Directs auth requests to the appropriate controller.
 */
fun Routing.auth() {
    post("/auth/login") {
        TODO("access sql database to authenticate user")
    }
    post("/auth/create") {
        TODO("access sql database to create user")
    }
}
