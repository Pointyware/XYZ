/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.pointyware.xyz.api.sessionAuthProvider

/**
 * Directs profile requests to the appropriate controller.
 */
fun Routing.profile() {
    authenticate(sessionAuthProvider) {
        route("/profile") {
            put {
                TODO("Update profile with arguments")
            }
            get("/{id}") {
                TODO("Get profile information")
            }
        }
    }
}
