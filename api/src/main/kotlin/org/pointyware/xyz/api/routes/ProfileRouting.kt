/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.pointyware.xyz.api.oauthProvider

/**
 * Directs profile requests to the appropriate controller.
 */
fun Routing.profile() {
    authenticate(oauthProvider) {
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
