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
import org.pointyware.xyz.feature.login.remote.ProfileService
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
        SystemFileSystem.delete(profileFile, mustExist = false)
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
        val riderId = Uuid(
            bytes = byteArrayOf(-74, -23, -37, 126, -61, -2, 78, 92,
                14, 69, 46, 84, -83, 75, -70, -45)
        )
        val driverId = Uuid(
            bytes = byteArrayOf(40, -78, 62, 32, 66, -57, 67, 9, -63,
                43, 76, -57, -102, -101, -65, 80
            )
        )

        /*
        When:
        - we retrieve a rider by id
         */
        val riderResult = fakeProfileService.getProfile(riderId)
        /*
        Then:
        - A Rider should be returned with the userId
         */
        assertTrue(riderResult.isSuccess)
        assertTrue(riderResult.getOrNull() is RiderProfile)

        /*
        When:
        - we retrieve a driver by id
         */
        val driverResult = fakeProfileService.getProfile(driverId)
        /*
        Then:
        - A Rider should be returned with the userId
         */
        assertTrue(driverResult.isSuccess)
        assertTrue(driverResult.getOrNull() is DriverProfile)

        /*
        When:
        - we retrieve an unknown user by id
         */
        val randomResult = fakeProfileService.getProfile(Uuid.v4())
        /*
        Then:
        - A failure should be returned
         */
        assertTrue(randomResult.isFailure)
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
          {
            "type": "org.pointyware.xyz.core.entities.profile.DriverProfile",
            "id": {
              "bytes": [40, -78, 62, 32, 66, -57, 67, 9, -63, 43, 76, -57, -102, -101, -65, 80]
            },
            "name": {
              "given": "John",
              "middle": "",
              "family": "Doe"
            },
            "gender": "Man",
            "picture": {
              "value": "https://example.com/johndoe.jpg"
            },
            "accommodations": [],
            "business": {
              "type": "org.pointyware.xyz.core.entities.business.Individual"
            }
          },
          {
            "type": "org.pointyware.xyz.core.entities.profile.RiderProfile",
            "id": {
              "bytes": [-74, -23, -37, 126, -61, -2, 78, 92, 14, 69, 46, 84, -83, 75, -70, -45]
            },
            "name": {
              "given": "Jane",
              "middle": "",
              "family": "Doe"
            },
            "gender": "Woman",
            "picture": {
              "value": "https://example.com/janedoe.jpg"
            },
            "disabilities": [
              {
                "type": "org.pointyware.xyz.core.entities.profile.Disability.ServiceAnimal"
              }
            ],
            "preferences": ""
          }
        ]
    """.trimIndent()
}
