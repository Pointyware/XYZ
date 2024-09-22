/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.datetime.Clock
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.entities.Name
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Rate.Companion.per
import org.pointyware.xyz.core.entities.business.dollarCents
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.geo.LatLong
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.entities.geo.Route
import org.pointyware.xyz.core.entities.geo.kilometers
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.core.local.di.coreLocalTestModule
import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.TestLocationService
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.core.navigation.di.homeQualifier
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.drive.data.ProviderTripRepository
import org.pointyware.xyz.drive.data.TestDriverSettingsRepository
import org.pointyware.xyz.drive.data.TestProviderTripRepository
import org.pointyware.xyz.drive.di.featureDriveDataTestModule
import org.pointyware.xyz.drive.entities.DriverRates
import org.pointyware.xyz.drive.entities.Request
import org.pointyware.xyz.drive.navigation.driverActiveRoute
import org.pointyware.xyz.drive.ui.ProviderDashboardScreen
import org.pointyware.xyz.drive.ui.ProviderDashboardScreenState
import org.pointyware.xyz.drive.viewmodels.ProviderDashboardViewModel
import org.pointyware.xyz.feature.drive.test.setupKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

/**
 * Tests for the DriveScreen composable
 */
@OptIn(ExperimentalTestApi::class)
class ProviderDashboardScreenUiTest {

    private lateinit var rideRepository: TestProviderTripRepository
    private lateinit var driverSettingsRepository: TestDriverSettingsRepository
    private lateinit var locationService: TestLocationService

    private lateinit var providerDashboardViewModel: ProviderDashboardViewModel
    private lateinit var navController: XyzNavController

    private val testRequest = Request(
        rideId = Uuid.v4(),
        rider = RiderProfile(
            id = Uuid.v4(),
            name = Name("John", "", "Doe"),
            picture = Uri.nullDevice,
            gender = Gender.NotSpecified,
            preferences = "",
            disabilities = emptySet()
        ),
        route = Route(
            start = Location(36.1145791,-97.1186917, "Walmart"),
            intermediates = emptyList(),
            end = Location(36.1160902,-97.0580495, "Walgreens"),
            distance = .5.kilometers(),
            duration = 1.0.minutes
        ),
        rate = 100L.dollarCents() per 1.0.kilometers(),
        timePosted = Clock.System.now()
    )

    @BeforeTest
    fun setUp() {
        setupKoin()
        loadKoinModules(listOf(
            coreLocalTestModule(),
            featureDriveDataTestModule(), // override the data module to expose TestDriverSettingsRepository
            module {
                single<Any>(qualifier = homeQualifier) { driverActiveRoute }
            },
        ))

        val koin = getKoin()
        rideRepository = koin.get<ProviderTripRepository>() as TestProviderTripRepository
        driverSettingsRepository = koin.get<TestDriverSettingsRepository>()
        locationService = koin.get()
        driverSettingsRepository.setDriverRates(DriverRates(
            maintenanceCost = 20L.dollarCents() per 1.0.kilometers(),
            pickupCost = 0L.dollarCents() per 1.0.kilometers(),
            dropoffCost = 100L.dollarCents() per 1.0.kilometers()
        ))

        providerDashboardViewModel = koin.get()
        navController = koin.get()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun wait_for_request_and_accept() = runComposeUiTest {
        /*
        Given:
        - The ride filter is set to accept all requests
        - The view model state is Idle
         */
        assertEquals(ProviderDashboardScreenState.AvailableRequests(emptyList()), providerDashboardViewModel.state.value, "initial state is idle")

        /*
        When:
        - The Provider Dashboard Screen is presented
        Then:
        - The Map is centered on the user
        - The requests list is present but empty
         */
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                ProviderDashboardScreen(
                    viewModel = providerDashboardViewModel,
                    navController = navController
                )
            }
        }
        onNodeWithContentDescription("Ride Requests")
            .assertExists()
            .onChild()
            .assertDoesNotExist()

        /*
        When:
        - A new trip request is received
        Then:
        - The requests list is visible
        - The new request is shown in the list
          - The request displays the user's name
          - The request displays the origin and destination
          - The request displays the route distance
          - The request displays the total fare
          - The request has a accept/reject buttons
         */
        rideRepository.addRequest(testRequest)
        waitForIdle()
        onNodeWithContentDescription("Ride Requests")
            .onChildren().onFirst().assertExists()
        onNodeWithText("John")
            .assertExists()
        onNodeWithText("Walmart")
            .assertExists()
        onNodeWithText("Walgreens")
            .assertExists()
        onNodeWithText(".50 km", substring = true)
            .assertExists()
        onNodeWithText("$0.50", substring = true)
            .assertExists()
        onNodeWithText("Accept")
            .assertExists()
        onNodeWithText("Reject")
            .assertExists()

        /*
        When:
        - The accept button is pressed
        Then:
        - The rider profile/messaging input is shown
        - The request list is absent
        - The provider status message displays "Picking up John"
        - The pick up button is present but disabled
         */
        onNodeWithText("Accept")
            .performClick()
        onNodeWithContentDescription("Rider Profile")
            .assertExists()
        onNodeWithContentDescription("Message Input")
            .assertExists()
        onNodeWithContentDescription("Ride Requests")
            .assertDoesNotExist()
        onNodeWithText("Picking up John")
            .assertExists()
        onNodeWithText("Pick Up")
            .assertExists()
            .assertIsNotEnabled()

        /*
        When:
        - The provider approaches the rider
        Then:
        - The pick up button is enabled
         */
        locationService.setLocation(LatLong(36.114579,-97.1184657))
        onNodeWithText("Pick Up")
            .assertIsEnabled()

        /*
        When:
        - The pick up button is pressed
        Then:
        - The provider status message displays "Driving John to Walgreens"
        - The pick up button is absent
        - The drop off button is present but disabled
         */
        onNodeWithText("Pick Up")
            .performClick()
        onNodeWithText("Driving John to Walgreens")
            .assertExists()
        onNodeWithText("Pick Up")
            .assertDoesNotExist()
        onNodeWithText("Drop Off")
            .assertExists()
            .assertIsNotEnabled()

        /*
        When:
        - The provider arrives at the destination
        Then:
        - The provider status message displays "Dropping off John"
        - The drop off button is enabled
         */
        locationService.setLocation(LatLong(36.1162121,-97.0583766))
        onNodeWithText("Dropping off John")
            .assertExists()
        onNodeWithText("Drop Off")
            .assertIsEnabled()

        /*
        When:
        - The drop off button is pressed
        Then:
        - The provider status message displays "Completed"
        - The drop off button is absent
        - The requests list is present
         */
        onNodeWithText("Drop Off")
            .performClick()
        onNodeWithText("Completed")
            .assertExists()
        onNodeWithContentDescription("Ride Requests")
            .assertExists()
    }

    @Test
    fun wait_for_request_and_reject() = runComposeUiTest {
        /*
        Given:
        - The ride filter is set to accept all requests
        - The view model state is Idle
         */
        assertEquals(ProviderDashboardScreenState.AvailableRequests(emptyList()), providerDashboardViewModel.state.value, "initial state is idle")

        /*
        When:
        - The Provider Dashboard Screen is presented
        Then:
        - The Map is centered on the user
        - The requests list is absent
         */
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                ProviderDashboardScreen(
                    viewModel = providerDashboardViewModel,
                    navController = navController
                )
            }
        }
        onNodeWithContentDescription("Ride Requests")
            .assertExists()
            .onChild()
            .assertDoesNotExist()

        /*
        When:
        - A new trip request is received
        Then:
        - The requests list is visible
        - The new request is shown in the list
          - The request displays the user's name
          - The request displays the origin and destination
          - The request displays the route distance
          - The request displays the total fare
          - The request has a accept/reject buttons
         */
        rideRepository.addRequest(testRequest)
        waitForIdle()
        onNodeWithContentDescription("Ride Requests")
            .onChildren().onFirst().assertExists()
        onNodeWithText("John")
            .assertExists()
        onNodeWithText("Walmart")
            .assertExists()
        onNodeWithText("Walgreens")
            .assertExists()
        onNodeWithText(".50 km", substring = true)
            .assertExists()
        onNodeWithText("$0.50", substring = true)
            .assertExists()
        onNodeWithText("Accept")
            .assertExists()
        onNodeWithText("Reject")
            .assertExists()

        /*
        When:
        - The reject button is pressed
        Then:
        - The request is removed from the list
        - The request list is absent
         */
        onNodeWithText("Reject")
            .performClick()
        onNodeWithContentDescription("New Requests")
            .assertDoesNotExist()
    }
}
