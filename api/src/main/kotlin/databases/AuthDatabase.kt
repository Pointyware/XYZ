package org.pointyware.xyz.api.databases

import org.pointyware.xyz.api.model.UserCredentials
import org.pointyware.xyz.api.services.UserService
import java.sql.Connection
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * This represents the authentication/authorization server for our users that may
 * be using a variety of services. It should be maintained with the consideration
 * that it will eventually be moved out to an external service.
 */
interface AuthDatabase {
    val users: AuthDao
}

/**
 * This interface represents the data access object (DAO) for user authentication and authorization.
 */
@OptIn(ExperimentalUuidApi::class)
interface AuthDao {
    suspend fun createUser(
        email: String,
        passHash: String,
        salt: String,
        resourcePermissions: List<String>
    ): Uuid
    suspend fun setUserPermissions(
        userId: String,
        resourcePermissions: List<String>
    )
    suspend fun getUserByEmail(email: String): UserDto
    suspend fun getUserById(userId: Uuid): UserDto
    suspend fun updateUser(user: UserDto)
    suspend fun deleteUser(userId: String)
    suspend fun insertAuthorization(userId: Uuid, token: String)
}

/**
 * This data class facilitates data transfer between the database and the application.
 */
data class UserDto(
    val id: String,
    val email: String,
    val passwordHash: String,
    val salt: String,
    val resourcePermissions: List<String>
)

@OptIn(ExperimentalUuidApi::class)
class AuthDatabaseImpl(
    private val connectionProvider: () -> Connection
) : AuthDatabase {

    private val connection by lazy {
        connectionProvider.invoke()
    }

    override val users: AuthDao
        get() = object : AuthDao {
            override suspend fun createUser(
                email: String,
                passHash: String,
                salt: String,
                resourcePermissions: List<String>
            ): Uuid {
                val userId = Uuid.random()
                connection.prepareStatement(
                    "INSERT INTO users (id, email, pass_hash, salt) VALUES (?, ?, ?)"
                ).apply {
                    setString(1, userId.toHexString())
                    setString(2, email)
                    setString(3, passHash)
                    setString(4, salt)
                }.executeUpdate()
                return userId
            }

            override suspend fun setUserPermissions(
                userId: String,
                resourcePermissions: List<String>
            ) {
                connection.prepareStatement(
                    "INSERT INTO user_permissions (email, permissions) VALUES (?, ?)"
                ).apply {
                    setString(1, userId)
                    setString(2, resourcePermissions.joinToString(","))
                }.executeUpdate()
            }

            override suspend fun getUserByEmail(email: String): UserDto {
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
                val permissions = connection.prepareStatement(
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
                return UserDto(
                    id = userCredentials.email,
                    email = userCredentials.email,
                    passwordHash = userCredentials.hash,
                    salt = userCredentials.salt,
                    resourcePermissions = permissions
                )
            }

            override suspend fun getUserById(userId: Uuid): UserDto {
                val userIdString = userId.toHexString()
                val userCredentials: UserCredentials = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?"
                ).apply {
                    setString(1, userIdString)
                }.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        UserCredentials(
                            email = resultSet.getString("email"),
                            hash = resultSet.getString("pass_hash"),
                            salt = resultSet.getString("salt")
                        )
                    } else {
                        throw IllegalArgumentException("User not found")
                    }
                }
                val permissions = connection.prepareStatement(
                    "SELECT * FROM user_permissions WHERE email = ?"
                ).apply {
                    setString(1, userIdString)
                }.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        resultSet.getString("permissions").split(",")
                    } else {
                        emptyList()
                    }
                }
                return UserDto(
                    id = userId.toHexString(),
                    email = userCredentials.email,
                    passwordHash = userCredentials.hash,
                    salt = userCredentials.salt,
                    resourcePermissions = permissions
                )
            }

            override suspend fun updateUser(user: UserDto) {
                connection.prepareStatement(
                    "UPDATE users SET email = ?, pass_hash = ?, salt = ? WHERE id = ?"
                ).apply {
                    setString(1, user.email)
                    setString(2, user.passwordHash)
                    setString(3, user.salt)
                    setString(4, user.id)
                }.executeUpdate()
            }

            override suspend fun deleteUser(userId: String) {
                connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?"
                ).apply {
                    setString(1, userId)
                }.executeUpdate()
            }

            override suspend fun insertAuthorization(userId: Uuid, token: String) {
                connection.prepareStatement(
                    "INSERT INTO authorizations (userId, token) VALUES (?, ?)"
                ).apply {
                    setString(1, userId.toHexString())
                    setString(2, token)
                }.executeUpdate()
            }
        }
}
