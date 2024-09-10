/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.waitUntilExactlyOneExists
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
        rate = 100L.dollarCents() per 1.0.kilometers()
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
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {

            }
        }

        onNodeWithContentDescription("New Requests")
            .assertDoesNotExist()

    }

    @Test
    fun wait_for_request_and_reject() = runComposeUiTest {
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {

            }
        }

        onNodeWithContentDescription("New Requests")
            .assertDoesNotExist()

    }
}
