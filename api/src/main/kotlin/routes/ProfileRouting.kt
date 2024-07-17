/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

/**
 * Directs auth requests to the appropriate controller.
 */
fun Routing.profile() {
    post("/profile") {
        TODO("Create profile with arguments")
    }
    get("/profile/{id}") {
        TODO("Get profile information")
    }
}
