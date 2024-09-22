/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.app.ride

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.navigation.StackNavigationController
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.feature.login.RiderProfileCreationScreen
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel
import org.pointyware.xyz.shared.di.setupKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * System/UI Test for Driver Profile Creation View
 */
@OptIn(ExperimentalTestApi::class)
class RiderProfileCreationUiTest {

    @BeforeTest
    fun setUp() {
        setupKoin()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun create_driver_profile() = runComposeUiTest {
        val di = getKoin()
        val viewModel = di.get<RiderProfileCreationViewModel>()
        val navController = di.get<StackNavigationController<Any, Any?>>()

        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                RiderProfileCreationScreen(
                    viewModel,
                    navController
                )
            }
        }

        /*
        When:
        - User Enters <given name>
        Then:
        - Create Profile button is disabled
         */
        onNodeWithText("Given Name")
            .assertExists()
            .performTextInput("John")
        onNodeWithText("Submit")
            .assertExists()
            .assertIsNotEnabled()

        /*
        When:
        - User Enters <middle name>
        Then:
        - Create Profile button is disabled
         */
        onNodeWithText("Middle Name")
            .assertExists()
            .performTextInput("Jacob")
        onNodeWithText("Submit")
            .assertExists()
            .assertIsNotEnabled()

        /*
        When:
        - User Enters <family name>
        Then:
        - Create Profile button is enabled
         */
        onNodeWithText("Family Name")
            .assertExists()
            .performTextInput("Jingleheimer Schmidt")
        onNodeWithText("Submit")
            .assertExists()
            .assertIsEnabled()

        /*
        When:
        - User Enters <birthdate>
        Then:
        - Create Profile button is disabled
         */
        // TODO: Implement date picker

        /*
        When:
        - User Selects <gender>
        Then:
        - Create Profile button is enabled
         */
        // TODO: Implement gender picker

        /*
        When:
        - User Taps "Add Disability"
        Then:
        - Disability Picker is shown
         */
        onNodeWithText("Add Disability")
            .assertExists()
            .performClick()
        onNodeWithText("Disability Picker")
            .assertExists()

        /*
        When:
        - User Taps "WheelchairAccess"
        Then:
        - Dialog is closed and Mobility is added to list of disabilities
         */
        onNodeWithText("Mobility")
            .assertExists()
            .performClick()
        onNodeWithText("Disability Picker")
            .assertDoesNotExist()

        /*
        When:
        - User Enters <preferences>
        Then:
        - Preferences are saved
         */
        onNodeWithText("Preferences")
            .assertExists()
            .performTextInput("I don't like small-talk")
        onNodeWithText("Preferences")
            .assertExists()
            .assert(hasText("I don't like small-talk"))

        /*
        When:
        - User Taps "Create Profile"
        Then:
        - Driver Profile is created
          - Profile contains <given name>, <middle name>, <family name>, <birthdate>, <gender>
          - Profile contains <disability>
          - Profile contains <preferences>
         */
        onNodeWithText("Submit")
            .assertExists()
            .performClick()
    }
}
