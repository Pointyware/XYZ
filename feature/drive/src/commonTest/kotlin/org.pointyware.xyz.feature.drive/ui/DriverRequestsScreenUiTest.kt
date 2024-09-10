/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.data.di.coreDataModule
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.entities.di.coreEntitiesModule
import org.pointyware.xyz.core.interactors.di.coreInteractorsModule
import org.pointyware.xyz.core.navigation.di.coreNavigationModule
import org.pointyware.xyz.core.navigation.di.homeQualifier
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.core.ui.di.coreUiModule
import org.pointyware.xyz.core.viewmodels.di.coreViewModelsModule
import org.pointyware.xyz.drive.data.RideRepository
import org.pointyware.xyz.drive.data.TestRideRepository
import org.pointyware.xyz.drive.di.featureDriveDataModule
import org.pointyware.xyz.drive.di.featureDriveModule
import org.pointyware.xyz.drive.navigation.driverActiveRoute
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
@OptIn(ExperimentalTestApi::class)
class DriverRequestsScreenUiTest {

    private lateinit var rideRepository: TestRideRepository

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
        unloadKoinModules(featureDriveDataModule())
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
    }

    @Test
    fun wait_for_request_and_reject() = runComposeUiTest {
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {

            }
        }
    }
}
