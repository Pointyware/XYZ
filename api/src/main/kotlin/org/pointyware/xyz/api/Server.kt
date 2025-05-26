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
import org.koin.core.context.startKoin
import org.pointyware.xyz.api.di.apiModule
import org.pointyware.xyz.api.routes.auth
import org.pointyware.xyz.api.routes.drive
import org.pointyware.xyz.api.routes.payment
import org.pointyware.xyz.api.routes.profile
import org.pointyware.xyz.api.routes.ride

/**
 * Main entry point for the XYZ API server.
 *
 * Consumes command line arguments to determine the port to run on. Default is 80.
 * ```shell
 * java -jar xyz-api.jar --port=<port>
 * ```
 */
fun main(vararg args: String) {
    var programInputs = ProgramInputs(port = 80)

    programInputs = args.iterator().processArgs(programInputs)

    startKoin {
        modules(
            apiModule()
        )
    }

    embeddedServer(Netty, programInputs.port) {
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

enum class CommandOption(
    val shortName: String,
    val longName: String,
    val onShortMatch: (Iterator<String>, ProgramInputs) -> ProgramInputs,
    val onLongMatch: (String, Iterator<String>, ProgramInputs) -> ProgramInputs
) {
    Port(
        "p",
        "port",
        onShortMatch = { args, inputs ->
            inputs.copy(port = args.next().toInt())
        },
        onLongMatch = { arg, args, inputs ->
            val longPrefix = "--${Port.longName}="
            when {
                arg == Port.longName -> {
                    inputs.copy(port = args.next().toInt())
                }
                arg.startsWith(longPrefix) -> {
                    inputs.copy(port = arg.substringAfter(longPrefix).toInt())
                }
                else -> throw IllegalArgumentException("Expected `--port <port>` or `--port=<port>`, but got `$arg`")
            }
        }
    )
}

data class ProgramInputs(
    val port: Int
)

private fun Iterator<String>.processArgs(inputs: ProgramInputs): ProgramInputs {
    var argState = inputs

    argLoop@
    while (hasNext()) {
        val arg = next()
        for (it in CommandOption.entries) {
            when {
                arg == "-${it.shortName}" -> {
                    argState = it.onShortMatch(this, argState)
                    continue@argLoop
                }
                arg.startsWith("--${it.longName}") -> {
                    argState = it.onLongMatch(arg, this, argState)
                    continue@argLoop
                }
            }
        }
        throw IllegalArgumentException("Unknown argument: $arg. Expected one of: ${CommandOption.entries.joinToString { it.longName }}")
    }

    return argState
}
