package org.pointyware.xyz.api.databases

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
    ): Result<Unit>
    suspend fun setUserPermissions(
        userId: String,
        resourcePermissions: List<String>
    ): Result<Unit>
    suspend fun getUserByEmail(email: String): Result<UserDto>
    suspend fun updateUser(user: UserDto): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
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
            ): Result<Unit> {
                TODO("Not yet implemented")
            }

            override suspend fun setUserPermissions(
                userId: String,
                resourcePermissions: List<String>
            ): Result<Unit> {
                TODO("Not yet implemented")
            }

            override suspend fun getUserByEmail(email: String): Result<UserDto> {
                TODO("Not yet implemented")
            }

            override suspend fun updateUser(user: UserDto): Result<Unit> {
                TODO("Not yet implemented")
            }

            override suspend fun deleteUser(userId: String): Result<Unit> {
                TODO("Not yet implemented")
            }
        }
}
