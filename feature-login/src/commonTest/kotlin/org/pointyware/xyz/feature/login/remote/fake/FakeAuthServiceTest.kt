/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote.fake

import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
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
import kotlinx.serialization.json.Json
import org.pointyware.xyz.core.data.DefaultLifecycleController
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.core.data.writeText
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
class FakeAuthServiceTest {

    lateinit var accountsFile: Path
    lateinit var lifecycleController: LifecycleController

    lateinit var fakeAuthService: FakeAuthService

    @BeforeTest
    fun setUp() {
        val tempDirectory = SystemTemporaryDirectory
        accountsFile = Path(tempDirectory, "accounts.json")
        lifecycleController = DefaultLifecycleController()

        val testDispatcher = StandardTestDispatcher()

        fakeAuthService = FakeAuthService(
            accountsFile = accountsFile,
            users = mutableMapOf(),

            json = Json { isLenient = true },

            lifecycleController = lifecycleController,
            dataContext = testDispatcher,
            dataScope = CoroutineScope(testDispatcher + SupervisorJob())
        )
        println("Created file reference: $accountsFile")

        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()

        println("Removing file reference: $accountsFile")
        SystemFileSystem.delete(accountsFile, mustExist = false)
    }

    @Test
    fun `fake should load users from file when initializing`() = runTest {
        /*
        Given:
        - some file with user text
        - a FakeAuthService
        - a lifecycle controller
         */
        accountsFile.writeText(accountsJsonString)
        fakeAuthService.loadFile(accountsFile)

        /*
        When:
        - a user attempts to login
         */
        val result = fakeAuthService.login("driver1@some.com", "password1")

        /*
        Then:
        - A success authorization should be returned with the userId
         */
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() is Authorization)
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
        - the test waits for work to complete
         */
        lifecycleController.onStop()
        testScheduler.advanceUntilIdle()

        /*
        Then:
        - the FakeProfileService should save the users
         */
        assertTrue(SystemFileSystem.exists(accountsFile))
    }

    private val accountsJsonString = """
        {
          "driver1@some.com": {
            "password": "password1",
            "id": {
              "bytes": [94, -106, -108, -36, -52, -57, 65, 46, 115, -40, 98, -3, 90, 69, 121, -81]
            }
          },
          "driver2@some.com": {
            "password": "password2",
            "id": {
              "bytes": [94, -106, -108, -36, -52, -57, 65, 46, 115, -40, 98, -3, 90, 69, 121, -82]
            }
          },
          "driver3@some.com": {
            "password": "password3",
            "id": {
              "bytes": [94, -106, -108, -36, -52, -57, 65, 46, 115, -40, 98, -3, 90, 69, 121, -83]
            }
          },
          "driver4@some.com": {
            "password": "password4",
            "id": {
              "bytes": [94, -106, -108, -36, -52, -57, 65, 46, 115, -40, 98, -3, 90, 69, 121, -84]
            }
          },
          "driver5@some.com": {
            "password": "password5",
            "id": {
              "bytes": [94, -106, -108, -36, -52, -57, 65, 46, 115, -40, 98, -3, 90, 69, 121, -85]
            }
          }
        }
    """.trimIndent()
}
