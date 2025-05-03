/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.controllers.PaymentsController

/**
 *
 */
fun Routing.ride() {
    get("/ride") {
        call.respondNullable<String?>("Hi rider!")
    }

    post("/ride/{id}/payment") {
        val id = call.parameters["id"]

        call.respondNullable<String?>("Hi rider! $id")
    }
}
