/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.pointyware.xyz.api.sessionAuthProvider

/**
 * Directs auth requests to the appropriate controller.
 */
fun Routing.profile() {
    authenticate(sessionAuthProvider) {
        route("/profile") {
            post {
                TODO("Create profile with arguments")
            }
            get("/{id}") {
                TODO("Get profile information")
            }
        }
    }
}
