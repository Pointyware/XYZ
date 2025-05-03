/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api

import com.stripe.Stripe
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.pointyware.xyz.api.controllers.PaymentsController
import org.pointyware.xyz.api.controllers.StripePaymentsController
import org.pointyware.xyz.api.routes.auth
import org.pointyware.xyz.api.routes.drive
import org.pointyware.xyz.api.routes.profile
import org.pointyware.xyz.api.routes.ride

fun main() {

    val port = System.getenv("PORT")?.toInt() ?: 8080

    Stripe.apiKey = System.getenv("STRIPE_API_KEY")

    startKoin {
        modules(
            module {
                factory<PaymentsController> {
                    StripePaymentsController()
                }
            }
        )
    }

    embeddedServer(Netty, port) {
        routing {
            auth()
            profile()

            ride()
            drive()
        }
    }.start(wait = true)
}
