/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.data.test

import org.pointyware.xyz.api.data.AuthDao
import org.pointyware.xyz.api.data.AuthRepository
import org.pointyware.xyz.api.data.SessionsDao
import org.pointyware.xyz.api.data.UserDto
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * A drop-in replacement for [org.pointyware.xyz.api.data.AuthRepositoryImpl] that uses an in-memory store to emulate the
 * production PostgreSQL database for the sake of easier local testing. A full end-to-end test
 * would require starting a PostgreSQL server and running the tests against it.
 */
@OptIn(ExperimentalUuidApi::class)
class TestAuthRepository(

): AuthRepository {

    private val userMap = mutableMapOf<String, UserDto>()
    private val authorizations = mutableMapOf<String, String>()

    override val users: AuthDao
        get() = object : AuthDao {

            override suspend fun getUserById(userId: Uuid): UserDto {
                val uuidString = userId.toHexString()
                return userMap[uuidString] ?: throw IllegalArgumentException("User not found")
            }

            override suspend fun insertAuthorization(userId: Uuid, token: String) {
                val uuidString = userId.toHexString()
                val user = userMap.values.firstOrNull { it.id == uuidString }
                    ?: throw IllegalArgumentException("User not found")
                authorizations[user.id] = token
            }

            override suspend fun createUser(
                email: String,
                passHash: String,
                resourcePermissions: List<String>
            ): Uuid {
                val userId = Uuid.random() // In a real database, this would be a generated ID
                val userIdString = userId.toHexString()
                val user = UserDto(userIdString, email, passHash, resourcePermissions)
                userMap[userIdString] = user
                return userId
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
        }

    override val session: SessionsDao
        get() = TODO("Not yet implemented")
}
