package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.databases.AuthRepository
import org.pointyware.xyz.core.data.dtos.Authorization
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Service for managing user credentials and authorizations.
 */
interface UserService {

    /**
     * Validates the user credentials for the given email and password and generates an
     * authorization token if valid.
     *
     * @throws InvalidCredentialsException if the email or password is invalid.
     */
    suspend fun validateCredentials(email: String, password: String): Authorization

    /**
     * Creates a new user with the given email and password. The password is hashed and salted
     * behind the scenes. See [EncryptionService] for more details.
     *
     * @throws ExistingEmailException
     */
    suspend fun createUser(email: String, password: String): Authorization

    /**
     * Exception thrown when user credentials are missing for the given email.
     */
    class InvalidCredentialsException(val email: String) : Exception("Password or email was wrong.")

    /**
     * Exception thrown when the email already exists in the database.
     */
    class ExistingEmailException(val email: String): Exception("Email already exists: $email")
}

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class UserServiceImpl(
    private val encryptionService: EncryptionService,
    private val authRepository: AuthRepository
) : UserService {

    override suspend fun validateCredentials(
        email: String,
        password: String
    ): Authorization {
        val credentials = authRepository.users.getUserByEmail(email)
        return if (encryptionService.matches(password, credentials.passwordHash)) {
            generateAuthorization(Uuid.parseHex(credentials.id)).getOrThrow()
        } else {
            throw Exception("Invalid credentials")
        }
    }

    /**
     * Generates an authorization token for the user with the given email.
     */
    private suspend fun generateAuthorization(userId: Uuid): Result<Authorization> = runCatching {
        val credentials = authRepository.users.getUserById(userId)
        val userPermissions = credentials.resourcePermissions
        val token = encryptionService.generateToken(userId, userPermissions).getOrThrow()
        val authorization = Authorization(userId = userId, token = token)
        authRepository.users.insertAuthorization(userId = userId, token = authorization.token)
        authorization
    }

    override suspend fun createUser(email: String, password: String): Authorization {
        val hash = encryptionService.saltedHash(password).getOrThrow()

        val newId = authRepository.users.createUser(email, hash, listOf())
        return generateAuthorization(newId).getOrThrow()
    }
}
