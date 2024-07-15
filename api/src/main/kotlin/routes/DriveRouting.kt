/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

/**
 *
 */
fun Routing.drive() {
    get("/drive") {
        call.respondNullable<String?>("Hi Driver!")
    }
}
