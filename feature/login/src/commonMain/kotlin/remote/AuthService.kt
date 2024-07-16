/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.remote

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
    fun login(email: String, password: String): Result<Authorization>

    /**
     * Creates a new user with the given email and password and returns an [Authorization] object.
     * If the email is already in use, the result will contain the [Authorization.InUseException].
     */
    fun createUser(email: String, password: String): Result<Authorization>
}

class SimpleAuthService(

): AuthService {

    override fun login(email: String, password: String): Result<Authorization> {
        TODO("Not yet implemented")
    }

    override fun createUser(email: String, password: String): Result<Authorization> {
        TODO("Not yet implemented")
    }
}

class TestAuthService(
    private val users: MutableMap<String, String> = mutableMapOf()
) {
    private val entropy = Random.Default
    data class TestAuthorization(
        override val email: String,
        override val token: String
    ): Authorization

    fun login(email: String, password: String): Result<Authorization> {
        if (users[email] == password) {
            return Result.success(TestAuthorization(email, entropy.nextInt().toString()))
        } else {
            return Result.failure(Authorization.InvalidCredentialsException())
        }
    }

    fun createUser(email: String, password: String): Result<Authorization> {
        if (users.containsKey(email)) {
            return Result.failure(Authorization.InUseException(email))
        } else {
            users[email] = password
            return Result.success(TestAuthorization(email, entropy.nextInt().toString()))
        }
    }
}
