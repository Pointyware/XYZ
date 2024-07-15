package org.pointyware.xyz.feature.login.data

import org.pointyware.xyz.core.entities.User
import org.pointyware.xyz.feature.login.local.UserCache
import org.pointyware.xyz.feature.login.remote.UserService

/**
 *
 */
interface UserRepository {
    suspend fun login(email: String, password: String): Result<User>

}

class UserRepositoryImpl(
    private val userCache: UserCache,
    private val userService: UserService
): UserRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return userService.login(email, password)
            .onSuccess {
                userCache.saveUser(it)
            }
            .onFailure {
                userCache.dropUser(email)
            }
    }
}
