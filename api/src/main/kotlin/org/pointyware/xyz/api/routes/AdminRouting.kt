package org.pointyware.xyz.api.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.pointyware.xyz.api.sessionAuthProvider

/**
 * Defines admin routes for the XYZ API.
 */
fun Routing.admin() {
    authenticate(sessionAuthProvider) {
        route("/admin") {
            get {
                // Placeholder for admin dashboard or info
                call.respondNullable("Admin endpoint is under construction.")
            }
        }
    }
}
