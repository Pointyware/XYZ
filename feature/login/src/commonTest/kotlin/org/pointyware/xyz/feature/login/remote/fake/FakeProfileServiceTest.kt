/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote.fake

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import kotlinx.io.readByteArray
import kotlinx.serialization.json.Json
import org.pointyware.xyz.core.data.DefaultLifecycleController
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.core.data.writeText
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.Name
import org.pointyware.xyz.core.entities.business.Individual
import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.entities.profile.Profile
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.feature.login.data.Authorization
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FakeProfileServiceTest {

    lateinit var profileFile: Path
    lateinit var lifecycleController: LifecycleController

    lateinit var fakeProfileService: FakeProfileService

    @BeforeTest
    fun setUp() {
        val tempDirectory = SystemTemporaryDirectory
        profileFile = Path(tempDirectory, "profiles.json")

        val testDispatcher = StandardTestDispatcher()

        lifecycleController = DefaultLifecycleController()
        fakeProfileService = FakeProfileService(
            profileFile = profileFile,
            profiles = mutableMapOf(),

            json = Json { isLenient = true },

            lifecycleController = lifecycleController,
            dataContext = testDispatcher,
            dataScope = CoroutineScope(testDispatcher + SupervisorJob())
        )
        println("Created file reference: $profileFile")

        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()

        println("Removing file reference: $profileFile")
//        SystemFileSystem.delete(profileFile, mustExist = false)
    }

    @Test
    fun `fake should load users from file when initializing`() = runTest {
        /*
        Given:
        - some file with profile text
        - a FakeProfileService
        - a lifecycle controller
         */
        profileFile.writeText(profilesJsonString)
        fakeProfileService.loadFile(profileFile)

        /*
        When:
        - we retrieve a user profile by id
         */
        val result = fakeProfileService.getProfile(Uuid.v4())

        /*
        Then:
        - A success authorization should be returned with the userId
         */
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() is RiderProfile)
    }

    @Test
    fun `fake should save users when lifecycle controller emits onStop`() = runTest {
        /*
        Given:
        - some DriverProfiles and RiderProfiles
        - a FakeProfileService
        - a lifecycle controller
        - the FakeProfileService has some users
         */
        val driver1 = DriverProfile(
            id = Uuid.v4(),
            name = Name("John", "", "Doe"),
            accommodations = emptySet(),
            gender = Gender.Man,
            picture = Uri("https://example.com/johndoe.jpg"),
            business = Individual
        )
        val rider1 = RiderProfile(
            id = Uuid.v4(),
            name = Name("Jane", "", "Doe"),
            gender = Gender.Woman,
            picture = Uri("https://example.com/janedoe.jpg"),
            preferences = "",
            disabilities = setOf(Disability.ServiceAnimal)
        )
        fakeProfileService.createDriverProfile(driver1.id, driver1)
        fakeProfileService.createRiderProfile(rider1.id, rider1)

        /*
        When:
        - the lifecycle controller emits onStop
         */
        lifecycleController.onStop()
        testScheduler.advanceUntilIdle()

        /*
        Then:
        - the FakeProfileService should save the users
         */
        assertTrue(SystemFileSystem.exists(profileFile))
    }

    private val profilesJsonString = """
        [
            
        ]
    """.trimIndent()
}
