/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.app.ride

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
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
import org.pointyware.xyz.drive.data.TestRideRepository
import org.pointyware.xyz.feature.login.RiderProfileCreationScreen
import org.pointyware.xyz.feature.login.data.CompanyRepository
import org.pointyware.xyz.feature.login.data.ProfileRepository
import org.pointyware.xyz.feature.login.data.TestCompanyRepository
import org.pointyware.xyz.feature.login.data.TestProfileRepository
import org.pointyware.xyz.feature.login.di.profileDataModule
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel
import org.pointyware.xyz.feature.ride.di.featureRideModule
import org.pointyware.xyz.feature.ride.ui.RideScreen
import org.pointyware.xyz.feature.ride.viewmodels.RideViewModel
import org.pointyware.xyz.shared.di.appModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * System/UI Test for Driver Profile Creation View
 */
@OptIn(ExperimentalTestApi::class)
class RequestRideUiTest {

    private lateinit var profileRepository: TestProfileRepository
    private lateinit var companyRepository: TestCompanyRepository
    private lateinit var rideRepository: TestRideRepository

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
        rideRepository = TestRideRepository(
            mutableSetOf(),
            koin.get(qualifier = dataQualifier)
        )
        unloadKoinModules(listOf(
            featureRideModule(),
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

        onNodeWithContentDescription("New Ride")
            .assertExists()
            .assertIsEnabled()

        /*
        When:
        - User clicks on the "New Ride" button
        Then:
        - The "New Ride" button transforms into the Search Bar
         */
        onNodeWithContentDescription("New Ride")
            .performClick()

        onNodeWithContentDescription("Search Bar")
            .assertExists()
        onNodeWithContentDescription("Confirm")
            .assertExists()
            .assertIsEnabled()

        /*
        When:
        - User types "Red Rock" into the Search Bar
        Then:
        - The Search Bar displays "Red Rock"
         */
        onNodeWithContentDescription("Search Bar")
            .performTextInput("Red Rock")
        onNodeWithContentDescription("Search Bar")
            .assert(hasText("Red Rock"))

        /*
        When:
        - User clicks on the "Confirm" button
        Then:
        - Location suggestion list is shown
         */
        onNodeWithContentDescription("Confirm")
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
        onNodeWithText("Red Rock")
            .performClick()
        // TODO: Assert that the map is updated
        onNodeWithContentDescription("Confirm Route")
            .assertExists()
            .assertIsEnabled()

        /*
        When:
        - User clicks on the "Confirm Route" button
        Then:
        - The "Cancel Request" button is shown
         */
        onNodeWithContentDescription("Confirm Route")
            .performClick()
        onNodeWithContentDescription("Cancel Request")
            .assertExists()
            .assertIsEnabled()
    }
}
