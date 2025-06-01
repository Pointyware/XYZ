/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote.fake

import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.pointyware.xyz.api.dtos.Authorization
import org.pointyware.xyz.feature.login.remote.AuthService
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class FakeAuthService(
    private val accountsFile: Path,
    private val users: MutableMap<String, UserEntry> = mutableMapOf(),
    private val defaultDelay: Long = 500,

    private val json: Json,

    private val dataContext: CoroutineContext,
    private val dataScope: CoroutineScope
): AuthService {

    init {
        dataScope.launch {
            loadFile(accountsFile)

//            lifecycleController.events.collect { state ->
//                when (state) {
//                    LifecycleEvent.Stop -> {
//                        writeFile() // TODO: trigger file write before closing program
//                    }
//                    else -> { /* do nothing */ }
//                }
//            }
        }
    }

    fun loadFile(file: Path) {
        SystemFileSystem.metadataOrNull(file)?.let { metadata ->
            val source = SystemFileSystem.source(file)

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

    @Serializable
    data class UserEntry(
        val password: String,
        val id: Uuid
    )

    override suspend fun login(email: String, password: String): Result<Authorization> {
        delay(defaultDelay)
        return users[email]?.takeIf { it.password == password }?.let {
            Result.success(Authorization(it.id, Random.nextInt().toString()))
        } ?: Result.failure(AuthService.InvalidCredentialsException())
    }

    override suspend fun createUser(email: String, password: String): Result<Authorization> {
        delay(defaultDelay)
        if (users.containsKey(email)) {
            return Result.failure(AuthService.InUseException(email))
        } else {
            val newUser = UserEntry(password, Uuid.random()).also { users[email] = it }
            return Result.success(Authorization(newUser.id, Random.nextInt().toString()))
        }
    }
}
