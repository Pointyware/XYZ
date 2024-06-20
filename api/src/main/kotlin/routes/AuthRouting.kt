package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

/**
 * Directs auth requests to the appropriate controller.
 */
fun Routing.auth() {
    get("/auth") {
        call.respondText("Hello World!")
    }
}
