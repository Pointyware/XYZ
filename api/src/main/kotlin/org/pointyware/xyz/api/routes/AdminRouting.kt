package org.pointyware.xyz.api.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import org.pointyware.xyz.api.sessionAuthProvider

fun Routing.admin() {
    authenticate(sessionAuthProvider) {

    }
    route("/admin") {

    }
}
