/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.datetime.Clock
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.data.di.coreDataModule
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Rate.Companion.per
import org.pointyware.xyz.core.entities.business.dollarCents
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.di.coreEntitiesModule
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.entities.geo.Route
import org.pointyware.xyz.core.entities.geo.kilometers
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.profile.Name
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.core.interactors.di.coreInteractorsModule
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.core.navigation.di.coreNavigationModule
import org.pointyware.xyz.core.navigation.di.homeQualifier
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.core.ui.di.coreUiModule
import org.pointyware.xyz.core.viewmodels.di.coreViewModelsModule
import org.pointyware.xyz.drive.data.RideRepository
import org.pointyware.xyz.drive.data.TestRideRepository
import org.pointyware.xyz.drive.di.featureDriveModule
import org.pointyware.xyz.drive.entities.Request
import org.pointyware.xyz.drive.navigation.driverActiveRoute
import org.pointyware.xyz.drive.ui.DriveScreen
import org.pointyware.xyz.drive.ui.DriveScreenState
import org.pointyware.xyz.drive.viewmodels.DriveViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

/**
 *
 */
@OptIn(ExperimentalTestApi::class)
class DriverRequestsScreenUiTest {

    private lateinit var rideRepository: TestRideRepository

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
            start = Location(0.0, 0.0, "Walmart"),
            intermediates = emptyList(),
            end = Location(1.0, 1.0, "Walgreens"),
            distance = .5.kilometers(),
            duration = 1.0.minutes
        ),
        rate = 100L.dollarCents() per 1.0.kilometers(),
        timePosted = Clock.System.now()
    )

    private fun testDataModule(
        testRideRepository: TestRideRepository
    ) = module {
        single<RideRepository> { testRideRepository }
    }

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                coreUiModule(),
                coreViewModelsModule(),
                coreInteractorsModule(),
                coreDataModule(),
                coreEntitiesModule(),
                coreNavigationModule(),

                featureDriveModule(),
                module {
                    single<Any>(qualifier = homeQualifier) { driverActiveRoute }
                }
            )
        }
        val koin = getKoin()
        rideRepository = TestRideRepository(koin.get(qualifier = dataQualifier))
        loadKoinModules(
            testDataModule(rideRepository)
        )

    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun wait_for_request_and_accept() = runComposeUiTest {
        val koin = getKoin()
        val viewModel = koin.get<DriveViewModel>()
        val navController = koin.get<XyzNavController>()

        /*
        Given:
        - The ride filter is set to accept all requests
        - The view model state is Idle
         */
        assertEquals(DriveScreenState.AvailableRequests(emptyList()), viewModel.state.value, "initial state is idle")

        /*
        When:
        - The Drive Screen is presented
        Then:
        - The Map is centered on the user
        - The requests list is present but empty
         */
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                DriveScreen(
                    viewModel = viewModel,
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
        - A new ride request is received
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
        onNodeWithText("0.5 km")
            .assertExists()
        onNodeWithText("$0.50")
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
         */
        onNodeWithText("Accept")
            .performClick()
        onNodeWithContentDescription("Rider Profile")
            .assertExists()
        onNodeWithContentDescription("Message Input")
            .assertExists()
    }

    @Test
    fun wait_for_request_and_reject() = runComposeUiTest {
        val koin = getKoin()
        val viewModel = koin.get<DriveViewModel>()
        val navController = koin.get<XyzNavController>()

        /*
        Given:
        - The ride filter is set to accept all requests
        - The view model state is Idle
         */
        assertEquals(DriveScreenState.AvailableRequests(emptyList()), viewModel.state.value, "initial state is idle")

        /*
        When:
        - The Drive Screen is presented
        Then:
        - The Map is centered on the user
        - The requests list is absent
         */
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                DriveScreen(
                    viewModel = viewModel,
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
        - A new ride request is received
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
        onNodeWithText("0.5 km")
            .assertExists()
        onNodeWithText("$0.50")
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
