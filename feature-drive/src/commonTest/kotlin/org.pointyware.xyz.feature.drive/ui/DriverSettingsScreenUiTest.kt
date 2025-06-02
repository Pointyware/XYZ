/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.entities.business.Rate.Companion.div
import org.pointyware.xyz.core.entities.business.dollarCents
import org.pointyware.xyz.core.entities.geo.LengthUnit
import org.pointyware.xyz.core.navigation.di.homeQualifier
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.drive.data.TestDriverSettingsRepository
import org.pointyware.xyz.drive.di.featureDriveDataTestModule
import org.pointyware.xyz.drive.entities.DriverRates
import org.pointyware.xyz.drive.navigation.driverSettings
import org.pointyware.xyz.drive.ui.DriverSettingsScreen
import org.pointyware.xyz.drive.viewmodels.DriverSettingsViewModel
import org.pointyware.xyz.feature.drive.test.setupKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 */
@OptIn(ExperimentalTestApi::class)
class DriverSettingsScreenUiTest {

    private lateinit var driverSettingsRepository: TestDriverSettingsRepository

    @BeforeTest
    fun setUp() {
        setupKoin()
        loadKoinModules(listOf(
            featureDriveDataTestModule(),
            module {
                single<Any>(qualifier = homeQualifier) { driverSettings }
            }
        ))
        val koin = getKoin()
        driverSettingsRepository = koin.get<TestDriverSettingsRepository>()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `revert ui on reject`() = runComposeUiTest {
        val koin = getKoin()
        driverSettingsRepository.setDriverRates(
            DriverRates(
                maintenanceCost = 12L.dollarCents() / LengthUnit.MILES,
                pickupCost = 34L.dollarCents() / LengthUnit.MILES,
                dropoffCost = 56L.dollarCents() / LengthUnit.MILES,
            )
        )
        val viewModel = koin.get<DriverSettingsViewModel>()

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
                DriverSettingsScreen(
                    viewModel = viewModel,
                )
            }
        }
        onNodeWithContentDescription("Cost of Maintenance", useUnmergedTree = true)
            .assertTextEquals("0.12")
        onNodeWithContentDescription("Pickup Rate", useUnmergedTree = true)
            .assertTextEquals("0.34")
        onNodeWithContentDescription("Dropoff Rate", useUnmergedTree = true)
            .assertTextEquals("0.56")

        onNodeWithContentDescription("Cost of Maintenance").apply {
            performTextClearance()
            performTextInput("98")
        }
        onNodeWithContentDescription("Pickup Rate").apply {
            performTextClearance()
            performTextInput("89.")
        }
        onNodeWithContentDescription("Dropoff Rate").apply {
            performTextClearance()
            performTextInput("76.54")
        }

        onNodeWithText("Revert")
            .performClick()

        val savedValue = driverSettingsRepository.getDriverRates()
        assertEquals(
            DriverRates(
                maintenanceCost = 12L.dollarCents() / LengthUnit.MILES,
                pickupCost = 34L.dollarCents() / LengthUnit.MILES,
                dropoffCost = 56L.dollarCents() / LengthUnit.MILES,
            ),
            savedValue
        )
    }

    @Test
    fun `retain input on accept`() = runComposeUiTest {
        val koin = getKoin()
        driverSettingsRepository.setDriverRates(
            DriverRates(
                maintenanceCost = 12L.dollarCents() / LengthUnit.MILES,
                pickupCost = 34L.dollarCents() / LengthUnit.MILES,
                dropoffCost = 56L.dollarCents() / LengthUnit.MILES,
            )
        )
        val viewModel = koin.get<DriverSettingsViewModel>()

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
                DriverSettingsScreen(
                    viewModel = viewModel,
                )
            }
        }
        onNodeWithContentDescription("Cost of Maintenance", useUnmergedTree = true)
            .assertTextEquals("0.12")
        onNodeWithContentDescription("Pickup Rate", useUnmergedTree = true)
            .assertTextEquals("0.34")
        onNodeWithContentDescription("Dropoff Rate", useUnmergedTree = true)
            .assertTextEquals("0.56")

        onNodeWithContentDescription("Cost of Maintenance").apply {
            performTextClearance()
            performTextInput("98")
        }
        onNodeWithContentDescription("Pickup Rate").apply {
            performTextClearance()
            performTextInput("89.")
        }
        onNodeWithContentDescription("Dropoff Rate").apply {
            performTextClearance()
            performTextInput("76.54")
        }

        onNodeWithText("Save")
            .performClick()

        val savedValue = driverSettingsRepository.getDriverRates()
        assertEquals(
            DriverRates(
                maintenanceCost = 9800L.dollarCents() / LengthUnit.MILES,
                pickupCost = 8900L.dollarCents() / LengthUnit.MILES,
                dropoffCost = 7654L.dollarCents() / LengthUnit.MILES,
            ),
            savedValue
        )
    }
}
