package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.model.UserCredentials
import org.pointyware.xyz.core.data.dtos.Authorization
import org.postgresql.Driver
import java.sql.Connection
import java.util.Properties

/**
 * Service for managing user credentials and authorizations.
 */
interface UserService {

    /**
     * Fetches the user credentials for the given email.
     * @throws MissingCredentialsException if the email is invalid.
     */
    suspend fun getUserCredentials(email: String): Result<UserCredentials>

    /**
     * Generates an authorization token for the user with the given email.
     */
    suspend fun generateAuthorization(email: String): Result<Authorization>

    /**
     * Creates a new user with the given email, password hash, and salt.
     */
    suspend fun createUser(email: String, hash: String, salt: String): Result<Unit>

    /**
     * Exception thrown when user credentials are missing for the given email.
     */
    class MissingCredentialsException(val email: String) : Exception("Missing credentials for email: $email")
}

/**
 *
 */
class PostgresUserService(
    private val encryptionService: EncryptionService,
    private val connection: Connection
) : UserService {

    override suspend fun getUserCredentials(email: String): Result<UserCredentials> = runCatching {
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
                throw UserService.MissingCredentialsException(email)
            }
        }
        userCredentials
    }

    override suspend fun generateAuthorization(email: String): Result<Authorization> = runCatching {
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

    override suspend fun createUser(email: String, hash: String, salt: String): Result<Unit> {
        return runCatching {
            connection.prepareStatement(
                "INSERT INTO users (email, pass_hash, salt) VALUES (?, ?, ?)"
            ).apply {
                setString(1, email)
                setString(2, hash)
                setString(3, salt)
            }.executeUpdate()
        }
    }
}
