/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.app.ride

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.navigation.StackNavigationController
import org.pointyware.xyz.core.remote.di.coreRemoteModule
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.drive.data.RideRepository
import org.pointyware.xyz.feature.login.data.CompanyRepository
import org.pointyware.xyz.feature.login.data.ProfileRepository
import org.pointyware.xyz.feature.login.data.TestCompanyRepository
import org.pointyware.xyz.feature.login.data.TestProfileRepository
import org.pointyware.xyz.feature.login.di.profileDataModule
import org.pointyware.xyz.feature.ride.data.TestRideRequestRepository
import org.pointyware.xyz.feature.ride.di.featureRideDataModule
import org.pointyware.xyz.feature.ride.ui.RideScreen
import org.pointyware.xyz.feature.ride.viewmodels.RideUiState
import org.pointyware.xyz.feature.ride.viewmodels.RideViewModel
import org.pointyware.xyz.shared.di.appModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * System/UI Test for Driver Profile Creation View
 */
@OptIn(ExperimentalTestApi::class)
class RequestRideUiTest {

    private lateinit var profileRepository: TestProfileRepository
    private lateinit var companyRepository: TestCompanyRepository
    private lateinit var rideRepository: TestRideRequestRepository

    private fun testFeatureProfileModule(
        profileRepository: ProfileRepository,
        companyRepository: CompanyRepository
    ) = module {
        single<ProfileRepository> { profileRepository }
        single<CompanyRepository> { companyRepository }
    }

    private fun testFeatureRideDataModule(
        rideRepository: RideRepository
    ) = module {
        single<RideRepository> { rideRepository }
    }

    @BeforeTest
    fun setUp() {
        profileRepository = TestProfileRepository()
        companyRepository = TestCompanyRepository()

        startKoin {
            modules(
                appModule()
            )
        }
        val koin = getKoin()
        rideRepository = TestRideRequestRepository(
            mutableSetOf(),
            koin.get(qualifier = dataQualifier)
        )
        unloadKoinModules(listOf(
            featureRideDataModule(),
            profileDataModule(),
            coreRemoteModule()
        ))
        loadKoinModules(listOf(
            testFeatureProfileModule(
                profileRepository,
                companyRepository
            )
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

        assertEquals(RideUiState.Idle, viewModel.state.value, "Initial state is Idle")

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
                RideScreen(
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
        - The "Confirm Route" button is shown
         */
        onNodeWithContentDescription("Location Suggestions")
            .onChildren().filterToOne(hasText("Red Rock", substring = true))
            .assertExists()
            .performClick()
        // TODO: Assert that the map is updated
        onNodeWithText("Confirm Route")
            .assertExists()
            .assertIsEnabled()

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
