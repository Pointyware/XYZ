/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api

import com.stripe.Stripe
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.xyz.api.controllers.PaymentsController
import org.pointyware.xyz.api.controllers.PaymentsControllerImpl
import org.pointyware.xyz.api.di.apiModule
import org.pointyware.xyz.api.routes.auth
import org.pointyware.xyz.api.routes.drive
import org.pointyware.xyz.api.routes.payment
import org.pointyware.xyz.api.routes.profile
import org.pointyware.xyz.api.routes.ride

fun main() {

    val port = System.getenv("PORT")?.toInt() ?: 8080

    startKoin {
        modules(
            apiModule()
        )
    }

    embeddedServer(Netty, port) {
        routing {
            auth()
            profile()

            ride()
            drive()

            payment()
        }
    }.start(wait = true)
}
