/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.util.encodeBase64
import org.pointyware.xyz.api.dtos.Authorization

/**
 * Defines authentication/authorization service functions.
 */
interface AuthService {
    /**
     * Logs in a user with the given email and password and returns an [Authorization] object.
     * If the email or password is incorrect, the result will contain the [Authorization.InvalidCredentialsException].
     */
    suspend fun login(email: String, password: String): Result<Authorization>

    /**
     * Creates a new user with the given email and password and returns an [Authorization] object.
     * If the email is already in use, the result will contain the [Authorization.InUseException].
     */
    suspend fun createUser(email: String, password: String): Result<Authorization>


    class InUseException(val email: String): Exception("Email ($email) is already in use.")
    class InvalidCredentialsException: Exception("Invalid email or password.")
}

/**
 * A simple implementation of [AuthService] that uses a [HttpClient] to make requests to the server.
 * For the server implementation, see [org.pointyware.xyz.api.routes.auth]
 */
class KtorAuthService(
    private val client: HttpClient
): AuthService {

    override suspend fun login(email: String, password: String): Result<Authorization> = runCatching {
        val response = client.post(Auth.Login()) {
            header("Authentication", "Basic ${"$email:$password".encodeToByteArray().encodeBase64()}")
            expectSuccess = true
        }
        response.body<Authorization>()
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> = runCatching {
        val response = client.post(Auth.Create()) {
            setBody(mapOf("email" to email, "password" to password))
            expectSuccess = true
        }
        response.body<Authorization>()
    }
}
