/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.wasm

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

const val ARG_PORT = "--port"
const val ENV_PORT = "PORT"

/**
 * Usage: `ServerKt --port=$PORT`
 */
fun main(vararg args: String) {
    val argPort = args.find { it.startsWith(ARG_PORT) }?.split("=")?.get(1)?.toInt()
    val port = argPort ?: System.getenv(ENV_PORT)?.toInt() ?: 8080
    embeddedServer(Netty, port) {
        routing {
            get() {

            }
        }
    }.start(wait = true)
}
