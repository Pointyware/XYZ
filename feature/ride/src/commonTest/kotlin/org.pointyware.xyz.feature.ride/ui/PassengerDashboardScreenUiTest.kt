/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.waitUntilDoesNotExist
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.navigation.StackNavigationController
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.feature.ride.di.featureRideDataTestModule
import org.pointyware.xyz.feature.ride.test.setupKoin
import org.pointyware.xyz.feature.ride.viewmodels.PassengerDashboardUiState
import org.pointyware.xyz.feature.ride.viewmodels.RideViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * System/UI Test for Rider Request Ride View
 */
@OptIn(ExperimentalTestApi::class)
class PassengerDashboardScreenUiTest {

    @BeforeTest
    fun setUp() {
        setupKoin()
        loadKoinModules(listOf(
            featureRideDataTestModule()
        ))
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun request_ride() = runComposeUiTest {
        val di = getKoin()
        val viewModel = di.get<RideViewModel>()
        val navController = di.get<StackNavigationController<Any, Any?>>()

        assertEquals(PassengerDashboardUiState.Idle, viewModel.state.value, "Initial state is Idle")

        /*
        Given:
        - User is on the Ride Screen
        - UiState is Idle
        When:
        - No Events
        Then:
        - The "New Ride" button is shown
         */

        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                PassengerDashboardScreen(
                    viewModel,
                    navController
                )
            }
        }


        onNodeWithText("New Ride")
            .assertExists()
            .assertIsEnabled()

        /*
        When:
        - User clicks on the "New Ride" button
        Then:
        - The "New Ride" button transforms into the Search Bar
        - The "Confirm" search button is shown but disabled
         */
        onNodeWithText("New Ride")
            .performClick()

        onNodeWithText("Search")
            .assertExists()
        onNodeWithText("Confirm")
            .assertExists()
            .assertIsNotEnabled()

        /*
        When:
        - User types "Red Rock" into the Search Bar
        Then:
        - The Search Bar displays "Red Rock"
        - The "Confirm" search button is enabled
         */
        onNodeWithText("Search")
            .performTextInput("Red Rock")
        onNodeWithText("Search")
            .assert(hasText("Red Rock"))
        onNodeWithText("Confirm")
            .assertIsEnabled()

        /*
        When:
        - User clicks on the "Confirm" button
        Then:
        - Location suggestion list is shown
         */
        onNodeWithText("Confirm")
            .performClick()
        onNodeWithContentDescription("Location Suggestions")
            .assertExists()

        /*
        When:
        - User clicks on the "Red Rock" suggestion
        Then:
        - The Map is updated with the "Red Rock" destination
        - The waiting indicator is shown while the route is calculated
        - The "Confirm Route" button is shown but disabled while the route is calculated
         */
        onNodeWithContentDescription("Location Suggestions")
            .onChildren().filterToOne(hasText("Red Rock", substring = true))
            .assertExists()
            .performClick()
        // TODO: Assert that the map is updated
        onNodeWithContentDescription("Loading")
            .assertExists()
        onNodeWithText("Confirm Route")
            .assertExists()
            .assertIsNotEnabled()

        waitUntilDoesNotExist(hasContentDescription("Loading"), 2000L)

        /*
        When:
        - The route is calculated
        Then:
        - The "Confirm Route" button is shown
        - The waiting indicator is no longer shown
         */
        onNodeWithText("Confirm Route")
            .assertExists()
            .assertIsEnabled()
        onNodeWithContentDescription("Loading")
            .assertDoesNotExist()

        /*
        When:
        - User clicks on the "Confirm Route" button
        Then:
        - The "Cancel Request" button is shown
         */
        onNodeWithText("Confirm Route")
            .performClick()
        onNodeWithText("Cancel Request")
            .assertExists()
            .assertIsEnabled()
    }
}
