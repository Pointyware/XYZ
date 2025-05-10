package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.model.UserCredentials
import org.pointyware.xyz.api.services.UserService.ExistingEmailException
import org.pointyware.xyz.api.services.UserService.InvalidCredentialsException
import org.pointyware.xyz.core.data.dtos.Authorization
import java.sql.Connection

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
class PostgresUserService(
    private val encryptionService: EncryptionService,
    // TODO: replace with database abstractions; the service should not be managing data
    //   directly
    private val connection: Connection,
) : UserService {

    /**
     * Fetches the user credentials for the given email.
     * @throws InvalidCredentialsException if the email is invalid.
     */
    private fun getUserCredentials(email: String): Result<UserCredentials> = runCatching {
        // TODO: replace with database abstractions; the service should not be managing data
        //   directly
        val userCredentials: UserCredentials = connection.prepareStatement(
            "SELECT * FROM users WHERE email = ?"
        ).apply {
            setString(1, email)
        }.executeQuery().use { resultSet ->
            if (resultSet.next()) {
                UserCredentials(
                    email = resultSet.getString("email"),
                    hash = resultSet.getString("pass_hash"),
                    salt = resultSet.getString("salt")
                )
            } else {
                throw UserService.InvalidCredentialsException(email)
            }
        }
        userCredentials
    }

    override suspend fun validateCredentials(
        email: String,
        password: String
    ): Authorization {
        val credentials = getUserCredentials(email).getOrThrow()
        val hash = encryptionService.saltedHash(password, credentials.salt).getOrThrow()
        return if (credentials.hash == hash) {
            generateAuthorization(email).getOrThrow()
        } else {
            throw Exception("Invalid credentials")
        }
    }

    /**
     * Generates an authorization token for the user with the given email.
     */
    private fun generateAuthorization(email: String): Result<Authorization> = runCatching {
        // TODO: replace with database abstractions; the service should not be managing data
        //   directly
        val userPermissions = connection.prepareStatement(
            "SELECT * FROM user_permissions WHERE email = ?"
        ).apply {
            setString(1, email)
        }.executeQuery().use { resultSet ->
            if (resultSet.next()) {
                resultSet.getString("permissions").split(",")
            } else {
                emptyList()
            }
        }
        val authorization = Authorization(
            email = email,
            token = encryptionService.generateToken(email, userPermissions).getOrThrow()
        )
        connection.prepareStatement(
            "INSERT INTO authorizations (email, token) VALUES (?, ?)"
        ).apply {
            setString(1, email)
            setString(2, authorization.token)
        }.executeUpdate()
        authorization
    }

    /**
     * Creates a new user with the given email, password hash, and salt.
     *
     * @throws ExistingEmailException
     */
    private fun createUser(email: String, hash: String, salt: String): Result<Unit> {
        // TODO: replace with database abstractions; the service should not be managing data
        //   directly
        return runCatching {
            connection.prepareStatement(
                "INSERT INTO users (email, pass_hash, salt) VALUES (?, ?, ?)"
            ).apply {
                setString(1, email)
                setString(2, hash)
                setString(3, salt)
            }.executeUpdate()
            Unit
        }
    }

    override suspend fun createUser(email: String, password: String): Authorization {
        val salt = encryptionService.generateSalt().getOrThrow()
        val hash = encryptionService.saltedHash(password, salt).getOrThrow()

        createUser(email, hash, salt).getOrThrow()
        return generateAuthorization(email).getOrThrow()
    }
}
