/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.core.data.LifecycleEvent
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.feature.login.data.Authorization
import kotlin.coroutines.CoroutineContext
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
class KtorAuthService(
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

/**
 *
 */
class TestAuthService(
    private val accountsFile: Path,
    private val users: MutableMap<String, UserEntry> = mutableMapOf(),
    private val defaultDelay: Long = 500,

    private val json: Json,

    private val lifecycleController: LifecycleController,
    private val dataContext: CoroutineContext,
    private val dataScope: CoroutineScope
): AuthService {
    private val entropy = Random.Default
    data class TestAuthorization(
        override val userId: Uuid,
        override val token: String
    ): Authorization

    data class UserEntry(
        val password: String,
        val id: Uuid
    )

    override suspend fun login(email: String, password: String): Result<Authorization> {
        delay(defaultDelay)
        return users[email]?.takeIf { it.password == password }?.let {
            Result.success(TestAuthorization(it.id, entropy.nextInt().toString()))
        } ?: Result.failure(Authorization.InvalidCredentialsException())
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> {
        delay(defaultDelay)
        if (users.containsKey(email)) {
            return Result.failure(Authorization.InUseException(email))
        } else {
            val newUser = UserEntry(password, Uuid.v4()).also { users[email] = it }
            return Result.success(TestAuthorization(newUser.id, entropy.nextInt().toString()))
        }
    }
}
