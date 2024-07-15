package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import org.pointyware.xyz.core.entities.User

/**
 *
 */
interface UserService {
    fun login(email: String, password: String): Result<User>
}

class KtorUserService(
    val client: HttpClient
): UserService {
    override fun login(email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }
}
