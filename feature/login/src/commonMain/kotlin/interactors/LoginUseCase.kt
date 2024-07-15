package org.pointyware.xyz.feature.login.interactors

import org.pointyware.xyz.core.entities.User
import org.pointyware.xyz.feature.login.data.UserRepository

/**
 */
class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return userRepository.login(email, password)
    }
}
