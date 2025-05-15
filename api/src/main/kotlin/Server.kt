/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext.startKoin
import org.pointyware.xyz.api.di.apiModule
import org.pointyware.xyz.api.routes.auth
import org.pointyware.xyz.api.routes.drive
import org.pointyware.xyz.api.routes.payment
import org.pointyware.xyz.api.routes.profile
import org.pointyware.xyz.api.routes.ride

fun main(vararg args: String) {
    // TODO: take port from args
    val port = 80

    startKoin {
        modules(
            apiModule()
        )
    }

    embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        routing {
            auth()
            profile()

            ride()
            drive()

            payment()
        }
    }.start(wait = true)
}
