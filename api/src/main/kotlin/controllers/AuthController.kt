package org.pointyware.xyz.api.controllers

import org.pointyware.xyz.api.services.EncryptionService
import org.pointyware.xyz.api.services.UserService
import org.pointyware.xyz.core.data.dtos.Authorization

interface AuthController {
    suspend fun login(email: String, password: String): Result<Authorization>
    suspend fun createUser(email: String, password: String): Result<Authorization>
}

class AuthControllerImpl(
    private val userService: UserService,
    private val encryptionService: EncryptionService
): AuthController {
    override suspend fun login(email: String, password: String): Result<Authorization> = runCatching {
        val credentials = userService.getUserCredentials(email)
        val hash = encryptionService.saltedHash(password, credentials.salt)
        return if (credentials.hash == hash) {
            Result.success(userService.generateAuthorization(email))
        } else {
            Result.failure(Exception("Invalid credentials"))
        }
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> {
        TODO("Not yet implemented")
    }
}
