/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote.fake

import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.Profile
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.feature.login.remote.ProfileService
import org.pointyware.xyz.feature.login.remote.fake.FakeAuthService.UserEntry
import kotlin.coroutines.CoroutineContext

/**
 * A test implementation of the [ProfileService] interface. Instead of relying on a remote service,
 * it will load and store profiles in a given directory between test runs.
 */
class FakeProfileService(
    private val profileFile: Path,
    private val profiles: MutableMap<Uuid, Profile> = mutableMapOf(),
    private val defaultDelay: Long = 500,

    private val json: Json,

    private val dataContext: CoroutineContext,
    private val dataScope: CoroutineScope
): ProfileService {

    init {
        dataScope.launch {
            loadFile(profileFile)

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
            if (jsonString.isBlank()) return
            val profileList = json.decodeFromString<List<Profile>>(jsonString)
            this.profiles.putAll(profileList.map { it.id to it })
        }
    }

    private fun writeFile() {
        val sink = SystemFileSystem.sink(profileFile)
        val jsonString = json.encodeToString(profiles.values.toList())
        val byteArray = jsonString.toByteArray(Charsets.UTF_8)
        val buffer = Buffer()
        buffer.write(byteArray)
        sink.write(buffer, buffer.size)
        sink.flush()
    }

    override suspend fun createDriverProfile(userId: Uuid, profile: DriverProfile): Result<DriverProfile> {
        profiles[userId] = profile
        return Result.success(profile)
    }

    override suspend fun createRiderProfile(userId: Uuid, profile: RiderProfile): Result<RiderProfile> {
        profiles[userId] = profile
        return Result.success(profile)
    }

    override suspend fun getProfile(userId: Uuid): Result<Profile?> {
        profiles[userId].let {
            return Result.success(it)
        }
    }

    override suspend fun updateProfile(userId: Uuid, profile: Profile): Result<Profile> {
        profiles[userId] = profile
        return Result.success(profile)
    }

    override suspend fun deleteProfile(userId: Uuid): Result<Unit> {
        profiles.remove(userId)
        return Result.success(Unit)
    }
}
