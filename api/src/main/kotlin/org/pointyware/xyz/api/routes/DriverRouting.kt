/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.sse.send
import io.ktor.server.sse.sse
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.controllers.OrderController
import org.pointyware.xyz.api.controllers.RideController
import org.pointyware.xyz.api.dtos.UserSession
import org.pointyware.xyz.api.oauthProvider

/**
 * Routes drive endpoint requests to the appropriate controller. Drive endpoints are meant
 * for driver use only.
 */
fun Routing.driver() {
    val koin = getKoin()
    authenticate(oauthProvider) {
        route("/driver") {
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

            sse("/orders", serialize = { typeInfo, it ->
                val serializer = Json.serializersModule.serializer(typeInfo.kotlinType!!)
                Json.encodeToString(serializer, it)
            }) {
                val session = call.principal<UserSession>()!!
                orderController.streamOrders(session.userId).collect { order ->
                    send(order)
                }
            }
        }
    }
}
