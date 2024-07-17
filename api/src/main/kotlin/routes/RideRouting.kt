/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

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
