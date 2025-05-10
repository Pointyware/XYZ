package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.model.UserCredentials
import org.pointyware.xyz.core.data.dtos.Authorization
import java.sql.Connection

/**
 * Service for managing user credentials and authorizations.
 */
interface UserService {

    /**
     * Fetches the user credentials for the given email.
     * @throws InvalidCredentialsException if the email is invalid.
     */
    @Deprecated("This gave responsibility to the consumer, but it should be the user service's responsibility to validate.", ReplaceWith("validateCredentials"))
    suspend fun getUserCredentials(email: String): Result<UserCredentials>

    /**
     * Validates the user credentials for the given email and password and generates an
     * authorization token if valid.
     *
     * @throws InvalidCredentialsException if the email or password is invalid.
     */
    suspend fun validateCredentials(email: String, password: String): Authorization

    /**
     * Generates an authorization token for the user with the given email.
     */
    @Deprecated("This gave responsibility to the consumer, but it should be the user service's responsibility to validate.", ReplaceWith("validateCredentials or createUser"))
    suspend fun generateAuthorization(email: String): Result<Authorization>

    /**
     * Creates a new user with the given email, password hash, and salt.
     */
    @Deprecated("This gave responsibility to the consumer to create the has, but cryptography should be the user service's responsibility.", ReplaceWith("createUser"))
    suspend fun createUser(email: String, hash: String, salt: String): Result<Unit>

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
    private val connection: Connection
) : UserService {

    @Deprecated(
        "This gave responsibility to the consumer, but it should be the user service's responsibility to validate.",
        replaceWith = ReplaceWith("validateCredentials")
    )
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
                throw UserService.InvalidCredentialsException(email)
            }
        }
        userCredentials
    }

    override suspend fun validateCredentials(
        email: String,
        password: String
    ): Authorization {
        TODO("Not yet implemented")
    }

    @Deprecated(
        "This gave responsibility to the consumer, but it should be the user service's responsibility to validate.",
        replaceWith = ReplaceWith("validateCredentials or createUser")
    )
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

    @Deprecated(
        "This gave responsibility to the consumer to create the hash, but cryptography should be the user service's responsibility.",
        replaceWith = ReplaceWith("createUser")
    )
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

    override suspend fun createUser(email: String, password: String): Authorization {
        TODO("Not yet implemented")
    }
}
