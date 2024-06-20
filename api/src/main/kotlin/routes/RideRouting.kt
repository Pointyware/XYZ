package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

/**
 *
 */
fun Routing.ride() {
    get("/ride") {
        call.respondNullable<String?>("Hi rider!")
    }
}
