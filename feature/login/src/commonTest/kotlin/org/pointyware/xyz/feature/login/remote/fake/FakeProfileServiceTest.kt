/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote.fake

import kotlinx.coroutines.test.runTest
import kotlinx.io.files.Path
import kotlinx.io.files.SystemTemporaryDirectory
import org.pointyware.xyz.core.data.DefaultLifecycleController
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.Name
import org.pointyware.xyz.core.entities.business.Individual
import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.entities.profile.RiderProfile
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * TODO: describe purpose/intent of FakeProfileServiceTest
 */
class FakeProfileServiceTest {

    lateinit var profileFile: Path
    lateinit var fakeProfileService: FakeProfileService
    lateinit var lifecycleController: LifecycleController

    @BeforeTest
    fun setUp() {
        val tempDirectory = SystemTemporaryDirectory
        lifecycleController = DefaultLifecycleController()

        fakeProfileService = FakeProfileService(
            tempDirectory
        )
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun `fake should load users from file when initializing`() {

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

        /*
        Then:
        - the FakeProfileService should save the users
         */

    }
}
