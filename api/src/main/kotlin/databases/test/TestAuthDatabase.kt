package org.pointyware.xyz.api.databases.test

import org.pointyware.xyz.api.databases.AuthDao
import org.pointyware.xyz.api.databases.AuthDatabase
import org.pointyware.xyz.api.databases.UserDto

/**
 * A drop-in replacement for [org.pointyware.xyz.api.databases.AuthDatabaseImpl] that uses an in-memory store to emulate the
 * production PostgreSQL database for the sake of easier local testing. A full end-to-end test
 * would require starting a PostgreSQL server and running the tests against it.
 */
class TestAuthDatabase(

): AuthDatabase {

    private val userMap = mutableMapOf<String, UserDto>()
    private val authorizations = mutableMapOf<String, String>()

    override val users: AuthDao
        get() = object : AuthDao {
            override suspend fun createUser(
                email: String,
                passHash: String,
                salt: String,
                resourcePermissions: List<String>
            ) {
                val userId = email // In a real database, this would be a generated ID
                val user = UserDto(userId, email, passHash, salt, resourcePermissions)
                userMap[userId] = user
            }

            override suspend fun setUserPermissions(
                userId: String,
                resourcePermissions: List<String>
            ) {
                val user = userMap[userId] ?: throw IllegalArgumentException("User not found")
                val updatedUser = user.copy(resourcePermissions = resourcePermissions)
                userMap[userId] = updatedUser
            }

            override suspend fun getUserByEmail(email: String): UserDto {
                return userMap.values.firstOrNull { it.email == email }
                    ?: throw IllegalArgumentException("User not found")
            }

            override suspend fun updateUser(user: UserDto) {
                userMap[user.id] = user
            }

            override suspend fun deleteUser(userId: String) {
                userMap.remove(userId)
            }

            override suspend fun insertAuthorization(email: String, token: String) {
                val user = userMap.values.firstOrNull { it.email == email }
                    ?: throw IllegalArgumentException("User not found")
                authorizations[user.id] = token
            }
        }
}
