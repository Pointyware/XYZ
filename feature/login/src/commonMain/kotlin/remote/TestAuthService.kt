/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.core.data.LifecycleEvent
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.feature.login.data.Authorization
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

/**
 *
 */
class TestAuthService(
    private val accountsFile: Path,
    private val users: MutableMap<String, UserEntry> = mutableMapOf(),
    private val defaultDelay: Long = 500,

    private val json: Json,

    private val lifecycleController: LifecycleController,
    private val dataContext: CoroutineContext,
    private val dataScope: CoroutineScope
): AuthService {

    init {
        dataScope.launch {
            readFile()

            lifecycleController.events.collect { state ->
                when (state) {
                    LifecycleEvent.Stop -> {
                        writeFile()
                    }
                    else -> { /* do nothing */ }
                }
            }
        }
    }

    private fun readFile() {
        SystemFileSystem.metadataOrNull(accountsFile)?.let { metadata ->
            val source = SystemFileSystem.source(accountsFile)

            val buffer = Buffer()
            source.readAtMostTo(buffer, Long.MAX_VALUE)
            val byteArray = buffer.readByteArray()
            val jsonString = byteArray.decodeToString()
            val users = json.decodeFromString<Map<String, UserEntry>>(jsonString)
            this.users.putAll(users)
        }
    }

    private fun writeFile() {
        val sink = SystemFileSystem.sink(accountsFile)
        val jsonString = json.encodeToString(users)
        val byteArray = jsonString.toByteArray(Charsets.UTF_8)
        val buffer = Buffer()
        buffer.write(byteArray)
        sink.write(buffer, buffer.size)
        sink.flush()
    }

    private val entropy = Random
    data class TestAuthorization(
        override val userId: Uuid,
        override val token: String
    ): Authorization

    data class UserEntry(
        val password: String,
        val id: Uuid
    )

    override suspend fun login(email: String, password: String): Result<Authorization> {
        delay(defaultDelay)
        return users[email]?.takeIf { it.password == password }?.let {
            Result.success(TestAuthorization(it.id, Random.nextInt().toString()))
        } ?: Result.failure(Authorization.InvalidCredentialsException())
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> {
        delay(defaultDelay)
        if (users.containsKey(email)) {
            return Result.failure(Authorization.InUseException(email))
        } else {
            val newUser = UserEntry(password, Uuid.v4()).also { users[email] = it }
            return Result.success(TestAuthorization(newUser.id, Random.nextInt().toString()))
        }
    }
}
