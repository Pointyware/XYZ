/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.controllers

import org.pointyware.xyz.api.services.UserService
import org.pointyware.xyz.api.dtos.Authorization
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Controller for handling authentication-related operations that return authorization tokens.
 */
//@AdapterLayer
@OptIn(ExperimentalUuidApi::class)
interface AuthController {
    suspend fun createUser(email: String, password: String): Result<Uuid>
    suspend fun createSession(uuidString: String, deviceInfo: String): Result<Uuid>
    suspend fun authenticate(email: String, password: String): Result<Authorization>
    suspend fun validateSession(sessionId: String): Result<Unit>

    class InvalidCredentialsException: IllegalArgumentException("Invalid credentials provided.")
    class ExistingEmailException: IllegalArgumentException("Email already exists.")
    class SessionNotFoundException: IllegalArgumentException("Session not found.")
}

/**
 *
 */
@ExperimentalUuidApi
class AuthControllerImpl(
    private val userService: UserService
): AuthController {
    override suspend fun createUser(email: String, password: String): Result<Uuid> = runCatching {
        userService.createUser(email, password).userId
    }
    override suspend fun createSession(uuidString: String, deviceInfo: String): Result<Uuid> {
        TODO("Not yet implemented")
    }
    override suspend fun authenticate(email: String, password: String): Result<Authorization> = runCatching {
        userService.validateCredentials(email, password)
    }
    override suspend fun validateSession(sessionId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}
