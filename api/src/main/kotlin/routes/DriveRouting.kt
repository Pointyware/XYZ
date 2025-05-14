/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.controllers.OrderController
import org.pointyware.xyz.api.controllers.RideController

/**
 * Routes drive endpoint requests to the appropriate controller. Drive endpoints are meant
 * for driver use only.
 */
fun Routing.drive() {
    val koin = getKoin()
    route("/drive") {
        val orderController = koin.get<OrderController>()
        val rideController = koin.get<RideController>()

        post("/start") {

        }
        put("/status") {

        }
        post("/accept") {

        }
        post("/stop") {

        }
    }
}
