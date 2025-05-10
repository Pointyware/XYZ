package org.pointyware.xyz.api.controllers

import org.pointyware.xyz.api.services.EncryptionService
import org.pointyware.xyz.api.services.UserService
import org.pointyware.xyz.core.data.dtos.Authorization

/**
 * Controller for handling authentication-related operations that return authorization tokens.
 */
//@AdapterLayer
interface AuthController {
    suspend fun login(email: String, password: String): Result<Authorization>
    suspend fun createUser(email: String, password: String): Result<Authorization>
}

class AuthControllerImpl(
    private val userService: UserService,
    private val encryptionService: EncryptionService
): AuthController {
    override suspend fun login(email: String, password: String): Result<Authorization> = runCatching {
        userService.validateCredentials(email, password)
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> = runCatching {
        userService.createUser(email, password)
    }
}
