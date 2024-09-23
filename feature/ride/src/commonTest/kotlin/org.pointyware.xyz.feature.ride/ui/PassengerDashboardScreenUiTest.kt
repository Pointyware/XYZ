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
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.entities.Name
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.core.navigation.di.homeQualifier
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.feature.ride.data.TestTripRepository
import org.pointyware.xyz.feature.ride.data.TripRepository
import org.pointyware.xyz.feature.ride.di.featureRideDataTestModule
import org.pointyware.xyz.feature.ride.entities.ExpirationDate
import org.pointyware.xyz.feature.ride.entities.PaymentMethod
import org.pointyware.xyz.feature.ride.local.FakePaymentStore
import org.pointyware.xyz.feature.ride.navigation.rideRoute
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

    private lateinit var tripRepository: TestTripRepository
    private lateinit var paymentStore: FakePaymentStore

    private lateinit var viewModel: RideViewModel
    private lateinit var navController: XyzNavController

    @BeforeTest
    fun setUp() {
        setupKoin()
        loadKoinModules(listOf(
            featureRideDataTestModule(),
            module {
                single<Any>(qualifier = homeQualifier) { rideRoute }
            }
        ))

        val koin = getKoin()

        tripRepository = koin.get()
        tripRepository.riderProfile = RiderProfile(
            id = Uuid.v4(),
            name = Name("Test", "", "Rider"),
            gender = Gender.Man,
            picture = Uri.nullDevice,
            preferences = "",
            disabilities = emptySet()
        )
        paymentStore = koin.get()
        paymentStore.savePaymentMethod(
            PaymentMethod(
                id = Uuid.v4(),
                lastFour = "3456",
                expiration = ExpirationDate(month = 12, year = 2024),
                cardholderName = "John Doe",
                paymentProvider = "Bisa"
            )
        )

        viewModel = koin.get()
        navController = koin.get()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun request_ride() = runComposeUiTest {
        /*
        Given:
        - User is on the Ride Screen
        - UiState is Idle
         */
        assertEquals(PassengerDashboardUiState.Idle, viewModel.state.value, "Initial state is Idle")

        /*
        When:
        - The Passenger Dashboard Screen is shown
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
        - The payment selection is shown
         */
        onNodeWithText("New Ride")
            .performClick()

        onNodeWithText("Search")
            .assertExists()
        onNodeWithText("Confirm")
            .assertExists()
            .assertIsNotEnabled()
        onNodeWithContentDescription("Payment Method")
            .assertExists()

        /*
        When:
        - User clicks on the "Payment Selection" button
        Then:
        - The "Payment Selection" button transforms into the Payment Method Selection
         */

        onNodeWithText("Select Payment Method")
            .performClick()

        onNodeWithContentDescription("Payment Method Selection")
            .assertExists()

        /*
        When:
        - User selects a payment method - Bisa
        Then:
        - The "Payment Method Selection" form transforms back into the "Payment Method" form
        - The selected payment method is shown
         */
        onNodeWithText("Bisa", substring = true)
            .performClick()

        onNodeWithContentDescription("Payment Method")
            .assertExists()
        onNodeWithText("Bisa", substring = true)
            .assertExists()
        onNodeWithText("Select Payment Method")
            .assertExists()

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

        /*
        When:
        - A driver accepts the request
        Then:
        - The driver profile information is shown
        - The messaging input is shown
        - The driver arriving message is shown
         */
        // TODO: trigger ride accepted event in repo



        /*
        When:
        - The driver picks up the rider
        Then:
        - The "Cancel Ride" button is shown
        - The messaging input is shown
        - The driver delivery message is shown
         */
        // TODO: trigger ride picked up event in repo

        /*
        When:
        - The driver drops off the rider
        Then:
        - The "Rate Driver" button is shown
        - The messaging input is removed
        - The progress message is removed
         */
        // TODO: trigger ride completed event in repo

    }
}
