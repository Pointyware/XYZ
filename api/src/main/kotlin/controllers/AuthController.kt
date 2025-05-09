package org.pointyware.xyz.api.controllers

import org.pointyware.xyz.api.services.EncryptionService
import org.pointyware.xyz.api.services.UserService
import org.pointyware.xyz.core.data.dtos.Authorization

/**
 *
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
        val credentials = userService.getUserCredentials(email).getOrThrow()
        val hash = encryptionService.saltedHash(password, credentials.salt).getOrThrow()
        if (credentials.hash == hash) {
            userService.generateAuthorization(email).getOrThrow()
        } else {
            throw Exception("Invalid credentials")
        }
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> =
        encryptionService.generateSalt().mapCatching { salt ->
            val hash = encryptionService.saltedHash(password, salt).getOrThrow()

            userService.createUser(email, hash, salt).getOrThrow()
            userService.generateAuthorization(email).getOrThrow()
        }
}
