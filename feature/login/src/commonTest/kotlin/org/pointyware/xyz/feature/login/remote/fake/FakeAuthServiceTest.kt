/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote.fake

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.test.runTest
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import kotlinx.io.readByteArray
import kotlinx.serialization.json.Json
import org.pointyware.xyz.core.data.DefaultLifecycleController
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.profile.Profile
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 *
 */
class FakeAuthServiceTest {

    lateinit var accountsFile: Path
    lateinit var lifecycleController: LifecycleController

    lateinit var fakeAuthService: FakeAuthService

    @BeforeTest
    fun setUp() {
        val tempDirectory = SystemTemporaryDirectory
        accountsFile = Path(tempDirectory, "accounts.json")
        lifecycleController = DefaultLifecycleController()

        fakeAuthService = FakeAuthService(
            accountsFile = accountsFile,
            users = mutableMapOf(),

            json = Json { isLenient = true },

            lifecycleController = lifecycleController,
            dataContext = Dispatchers.IO,
            dataScope = CoroutineScope(Dispatchers.IO)
        )
        println("Created file reference: $accountsFile")
    }

    @AfterTest
    fun tearDown() {
        println("Removing file reference: $accountsFile")
        SystemFileSystem.delete(accountsFile, mustExist = false)
    }

    @Test
    fun `fake should load users from file when initializing`() {

    }

    @Test
    fun `fake should save users when lifecycle controller emits onStop`()  = runTest {
        /*
        Given:
        - some email and password
        - a FakeAuthService
        - a lifecycle controller
         */
        val email = "driver1@some.com"
        val password = "password1"
        fakeAuthService.createUser(email, password)

        /*
        When:
        - the lifecycle controller emits Stop
         */
        lifecycleController.onStop()

        /*
        Then:
        - the FakeProfileService should save the users
         */
        assertTrue(SystemFileSystem.exists(accountsFile))
    }
}
