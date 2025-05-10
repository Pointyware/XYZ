package org.pointyware.xyz.api.databases

import org.pointyware.xyz.api.model.UserCredentials
import org.pointyware.xyz.api.services.UserService

/**
 * This represents the authentication/authorization server for our users that may
 * be using a variety of services. It should be maintained with the consideration
 * that it will eventually be moved out to an external service.
 */
interface AuthDatabase {
    val users: AuthDao
}

interface AuthDao {
    suspend fun createUser(
        email: String,
        passHash: String,
        salt: String,
        resourcePermissions: List<String>
    )
    suspend fun setUserPermissions(
        userId: String,
        resourcePermissions: List<String>
    )
    suspend fun getUserByEmail(email: String): UserDto
    suspend fun updateUser(user: UserDto)
    suspend fun deleteUser(userId: String)
    suspend fun insertAuthorization(email: String, token: String)
}

data class UserDto(
    val id: String,
    val email: String,
    val passwordHash: String,
    val salt: String,
    val resourcePermissions: List<String>
)

class AuthDatabaseImpl(
    private val connectionFactory: PostgresConnectionFactory
) : AuthDatabase {

    private val connection by lazy {
        connectionFactory.createConnection()
    }

    override val users: AuthDao
        get() = object : AuthDao {
            override suspend fun createUser(
                email: String,
                passHash: String,
                salt: String,
                resourcePermissions: List<String>
            ) {
                connection.prepareStatement(
                    "INSERT INTO users (email, pass_hash, salt) VALUES (?, ?, ?)"
                ).apply {
                    setString(1, email)
                    setString(2, passHash)
                    setString(3, salt)
                }.executeUpdate()
            }

            override suspend fun setUserPermissions(
                userId: String,
                resourcePermissions: List<String>
            ) {
                TODO("Not yet implemented")
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

            override suspend fun updateUser(user: UserDto) {
                TODO("Not yet implemented")
            }

            override suspend fun deleteUser(userId: String) {
                TODO("Not yet implemented")
            }

            override suspend fun insertAuthorization(email: String, token: String) {
                connection.prepareStatement(
                    "INSERT INTO authorizations (email, token) VALUES (?, ?)"
                ).apply {
                    setString(1, email)
                    setString(2, token)
                }.executeUpdate()
            }
        }
}
