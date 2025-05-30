/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.sse.sse
import org.pointyware.xyz.api.sessionAuthProvider

/**
 *
 */
fun Routing.rider() {
    authenticate(sessionAuthProvider) {
        route("/rider") {
            get {
                call.respondNullable<String?>("Hi rider!")
            }
            post("/{id}/payment") {
                val id = call.parameters["id"]

                call.respondNullable<String?>("Hi rider! $id")
            }
            sse("/request/{id}") {
                val id = call.parameters["id"]
                // TODO: Send stream of ride events for the rider
            }
            post("/request") {
                // create a new ride request
                sse {
                    // TODO: Send stream of ride events for the rider
                }
            }
        }
    }
}
