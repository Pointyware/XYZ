/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import org.pointyware.xyz.feature.login.data.Authorization
import kotlin.random.Random

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
}

/**
 * A simple implementation of [AuthService] that uses a [HttpClient] to make requests to the server.
 * For the server implementation, see [org.pointyware.xyz.api.routes.auth]
 */
class SimpleAuthService(
    private val client: HttpClient
): AuthService {

    override suspend fun login(email: String, password: String): Result<Authorization> {
        try {
            val response = client.post(Auth.Login) {
                setBody(mapOf("email" to email, "password" to password))
            }
            return Result.success(response.body<AuthorizationDto>())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> {
        try {
            val response = client.post(Auth.Create) {
                setBody(mapOf("email" to email, "password" to password))
            }
            return Result.success(response.body<AuthorizationDto>())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}

class TestAuthService(
    private val users: MutableMap<String, String> = mutableMapOf()
): AuthService {
    private val entropy = Random.Default
    data class TestAuthorization(
        override val email: String,
        override val token: String
    ): Authorization

    override suspend fun login(email: String, password: String): Result<Authorization> {
        return if (users[email] == password) {
            Result.success(TestAuthorization(email, entropy.nextInt().toString()))
        } else {
            Result.failure(Authorization.InvalidCredentialsException())
        }
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> {
        if (users.containsKey(email)) {
            return Result.failure(Authorization.InUseException(email))
        } else {
            users[email] = password
            return Result.success(TestAuthorization(email, entropy.nextInt().toString()))
        }
    }
}
