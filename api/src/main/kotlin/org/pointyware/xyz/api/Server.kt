/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
import io.ktor.server.auth.oauth
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
import org.pointyware.xyz.api.dtos.UserSession
import org.pointyware.xyz.api.dtos.auth.AuthorizationError
import org.pointyware.xyz.api.routes.admin
import org.pointyware.xyz.api.routes.auth
import org.pointyware.xyz.api.routes.driver
import org.pointyware.xyz.api.routes.profile
import org.pointyware.xyz.api.routes.rider
import kotlin.uuid.ExperimentalUuidApi

const val basicAuthProvider = "basic_auth"
const val oauthProvider = "oauth_auth"
const val sessionAuthHeader = "X-Session-Id"

private val serverClient = HttpClient(CIO) {
    install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}

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

    programInputs = args.iterator().processArgs(
        programInputs,
        listOf(
            PortOption
        )
    )

    startKoin {
        modules(
            apiModule()
        )
    }

    embeddedServer(Netty, programInputs.port) {
        commonModule()
        authModule()
        resourceModule()
    }.start(wait = true)
}

/**
 * Installs common plugins and configurations for the application.
 * - ContentNegotiation: for JSON serialization
 * - Sessions: for user session management
 * - Authentication: for session-based authentication
 */
@OptIn(ExperimentalUuidApi::class)
fun Application.commonModule() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Sessions) {
//        val fileStorage = CacheStorage(
//            directorySessionStorage(File(".")), 60 * 60 * 1000L // 1 hour cache
//        )
//        val redisStorage = RedisSessionStorage
        val storage: SessionStorage = SessionStorageMemory()
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
                    .onFailure { error ->
                        when (error) {
                            is AuthController.InvalidCredentialsException -> {
                                application.environment.log.error("Authentication error: ${error.message}", error)
                                respondNullable(HttpStatusCode.Unauthorized, AuthorizationError())
                            }
                            else -> {
                                respondNullable(HttpStatusCode.InternalServerError)
                                return@validate null
                            }
                        }
                        return@validate null
                    }
                    .getOrThrow()
                UserIdPrincipal(authorization.userId.toHexString())
            }
        }
//        session<UserSession>(sessionAuthProvider) { // requires the `Sessions` plugin to be installed
//            validate { session ->
//                val koin = getKoin()
//                val authController = koin.get<AuthController>()
//                authController.validateSession(session.sessionId)
//                    .onFailure { return@validate null }
//                UserIdPrincipal(session.sessionId)
//            }
//            challenge {
//                call.respond(HttpStatusCode.Unauthorized, AuthorizationError())
//            }
//        }
        oauth(oauthProvider) {
            // provides a url that will be used to redirect the user after successful authentication
            urlProvider = { _ -> "${BuildConfig.PROTOCOL}://${BuildConfig.XYZ_HOST}/auth/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "pointyware_oauth",
                    authorizeUrl = "${BuildConfig.PROTOCOL}://${BuildConfig.POINTYWARE_HOST}/auth/authorize",
                    accessTokenUrl = "${BuildConfig.PROTOCOL}://${BuildConfig.POINTYWARE_HOST}/auth/token",
                    requestMethod = HttpMethod.Post,
                    clientId = "XYZ_CLIENT_ID", // TODO: replace with actual client ID
                    clientSecret = "XYZ_CLIENT_SECRET", // TODO: replace with actual client secret
                    defaultScopes = listOf("https://api.pointyware.org/auth/user.profile"), // TODO: define all scopes needed
                    onStateCreated = { call, state ->
                        // save state in redirect url for PKCE (Proof Key for Code Exchange)
                        call.request.queryParameters["redirectUrl"]?.let { redirectUrl ->
                            // TODO: add (state to redirectUrl) to a persistent store; koin.get<...>().saveRedirectUrl(state, redirectUrl)
                        }
                    }
                )
            }
            client = serverClient
        }
    }
}

/**
 * Installs authentication routes and handlers for the application.
 * - Basic Authentication: for user login and session creation
 */
fun Application.authModule() {
    routing {
        admin()
        auth()
        profile()
    }
}

/**
 * Installs resource routes for the application.
 * - SSE: for server-sent events
 * - Authentication: for session-based authentication
 */
fun Application.resourceModule() {
    install(SSE)
    routing {
        rider()
        driver()
    }
}

data class CommandOption(
    val shortName: String,
    val longName: String,
    val onShortMatch: (Iterator<String>, ProgramInputs) -> ProgramInputs,
    val onLongMatch: (String, Iterator<String>, ProgramInputs) -> ProgramInputs
)

private const val PortOptionShort = "p"
private const val PortOptionLong = "port"
val PortOption = CommandOption(
    PortOptionShort,
    PortOptionLong,
    onShortMatch = { args, inputs ->
        inputs.copy(port = args.next().toInt())
    },
    onLongMatch = { arg, args, inputs ->
        val longPrefix = "--$PortOptionLong="
        when {
            arg == PortOptionLong -> {
                inputs.copy(port = args.next().toInt())
            }
            arg.startsWith(longPrefix) -> {
                inputs.copy(port = arg.substringAfter(longPrefix).toInt())
            }
            else -> throw IllegalArgumentException(
                "Expected `--$PortOptionLong <port>` or `--$PortOptionLong=<port>`, but got `$arg`"
            )
        }
    }
)

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

/**
 * Processes command line arguments and returns the updated [ProgramInputs].
 *
 * @param inputs The initial program inputs.
 * @param commands The list of command options to process against.
 * @return The updated program inputs after processing the arguments.
 * @throws IllegalArgumentException if an unknown argument is encountered.
 */
private fun Iterator<String>.processArgs(
    inputs: ProgramInputs,
    commands: List<CommandOption>
): ProgramInputs {
    var argState = inputs

    argLoop@
    while (hasNext()) {
        val arg = next()
        for (it in commands) {
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
