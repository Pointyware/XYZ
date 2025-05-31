/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
import io.ktor.server.auth.session
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.routing
import io.ktor.server.sessions.SessionStorage
import io.ktor.server.sessions.SessionStorageMemory
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.header
import io.ktor.server.sse.SSE
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.ktor.ext.getKoin
import org.pointyware.xyz.api.controllers.AuthController
import org.pointyware.xyz.api.di.apiModule
import org.pointyware.xyz.api.routes.auth
import org.pointyware.xyz.api.routes.driver
import org.pointyware.xyz.api.routes.payment
import org.pointyware.xyz.api.routes.profile
import org.pointyware.xyz.api.routes.rider
import org.pointyware.xyz.core.data.dtos.UserSession
import kotlin.uuid.ExperimentalUuidApi

const val basicAuthProvider = "basic_auth"
const val sessionAuthProvider = "session_auth"
const val sessionAuthHeader = "X-Session-Id"

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
        module()
    }.start(wait = true)
}

@OptIn(ExperimentalUuidApi::class)
fun Application.module() {
    install(SSE)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Sessions) {
        val storage: SessionStorage = SessionStorageMemory() // swap to redis and/or database in production
        header<UserSession>(sessionAuthHeader, storage)
    }
    install(Authentication) {
        basic(basicAuthProvider) {
            realm = "XYZ API"
            charset = Charsets.UTF_8
            validate { credentials ->
                val koin = getKoin()
                val authController = koin.get<AuthController>()
                val authorization = authController.authenticate(credentials.name, credentials.password)
                    .onFailure { return@validate null }
                    .getOrThrow()
                UserIdPrincipal(authorization.userId.toHexString())
            }
        }
        session<UserSession>(sessionAuthProvider) {
            validate { session ->
                val koin = getKoin()
                val authController = koin.get<AuthController>()
                authController.validateSession(session.sessionId)
                    .onFailure { return@validate null }
                UserIdPrincipal(session.sessionId)
            }
            challenge {
                call.respondNullable(HttpStatusCode.Unauthorized)
//                    call.respondRedirect("/auth/login?referrer=${call.request.uri}")
            }
        }
    }
    routing {
        auth()
        profile()

        rider()
        driver()

        payment()
    }
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

private fun printUsage() {
    println(
        """
        Usage: java -jar xyz-api.jar [options]
        
        Options:
          -p <port>, --port=<port>       Port to run the server on (default: 80)
        
        Example:
          java -jar xyz-api.jar --port=8080
        """.trimIndent()
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
        printUsage()
        throw IllegalArgumentException("Unknown argument: $arg.")
    }

    return argState
}
