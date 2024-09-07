/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.app.drive

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.navigation.StackNavigationController
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.feature.login.DriverProfileCreationScreen
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * System/UI Test for Driver Profile Creation View
 */
@OptIn(ExperimentalTestApi::class)
class DriverProfileCreationUiTest {

    @BeforeTest
    fun setUp() {
        // TODO: Setup test server for repository
        koinApplication {
            modules(

            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun create_driver_profile() = runComposeUiTest {
        val di = getKoin()
        val viewModel = di.get<DriverProfileCreationViewModel>()
        val navController = di.get<StackNavigationController<Any, Any?>>()


        /*
        Given:
        - User is logged out
        - Account does not exist for <email>
         */


        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                DriverProfileCreationScreen(
                    viewModel,
                    navController
                )
            }
        }

        /*
        When:
        - User Opens App
        Then:
        - User is presented with Login Screen
         */

        /*
        When:
        - User Selects Create Account
        Then:
        - Login Screen shows Create Account Form
         */

        /*
        When:
        - User Enters <email>, <password>, <confirm password>
        Then:
        - Create Account Button is enabled
         */

        /*
        When:
        - User Selects Create Account
        Then:
        - Account is created for <email>
        - User is presented with Profile Creation Screen
        - Profile Creation Screen shows "Rider" Tab selected
         */

        /*
        When:
        - User Enters <given name>, <middle name>, <family name>
        Then:
        - Create Profile button is disabled
         */

        /*
        When:
        - User Enters <birthdate>
        Then:
        - Create Profile button is disabled
         */

        /*
        When:
        - User Selects <gender>
        Then:
        - Create Profile button is enabled
         */

        /*
        When:
        - User Taps "Driver" Tab
        Then:
        - Driver Profile Form is displayed
        - Company Picker shows "Independent" selected
         */

        /*
        When:
        - User Taps "Add Accommodation"
        Then:
        - Accommodation Picker is displayed
         */

        /*
        When:
        - User Selects <accommodation>
        Then:
        - Accommodation is added to Profile
         */

        /*
        When:
        - User Taps "Create Profile"
        Then:
        - Driver Profile is created
          - Profile contains <given name>, <middle name>, <family name>, <birthdate>, <gender>
          - Profile contains <accommodation>
          - Profile company is "Independent"
         */
    }
}
