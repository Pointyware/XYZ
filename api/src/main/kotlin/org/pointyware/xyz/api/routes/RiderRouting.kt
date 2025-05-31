/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.sse.sse
import org.koin.ktor.ext.getKoin
import org.pointyware.xyz.api.controllers.PaymentsController
import org.pointyware.xyz.api.sessionAuthProvider

/**
 * Defines Rider endpoints and routes requests to the appropriate controller.
 */
fun Routing.rider() {
    val koin = getKoin()
    authenticate(sessionAuthProvider) {
        route("/rider") {
            // Implemented per: https://docs.stripe.com/connect/direct-charges?platform=android#add-server-endpoint
            post("/{id}/payment-intent") {
                val customerId = call.parameters["id"] ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Customer ID is required")
                    return@post
                }
                // Collect Call Information
                // Create the Payment Intent for the Completed Ride

                val paymentsController = koin.get<PaymentsController>()
                val clientSecretMap = paymentsController
                    .createPaymentIntent(customerId)
                    .map {
                        mapOf(
                            "client_secret" to it,
                        )
                    }
                call.respondResult(clientSecretMap)
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
