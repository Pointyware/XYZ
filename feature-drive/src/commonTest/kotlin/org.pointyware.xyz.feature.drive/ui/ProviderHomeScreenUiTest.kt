/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.core.navigation.di.homeQualifier
import org.pointyware.xyz.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.drive.navigation.driverActiveRoute
import org.pointyware.xyz.drive.navigation.driverHomeRoute
import org.pointyware.xyz.drive.ui.ProviderHomeScreen
import org.pointyware.xyz.feature.drive.test.setupKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 */
@OptIn(ExperimentalTestApi::class)
class ProviderHomeScreenUiTest {

    @BeforeTest
    fun setUp() {
        setupKoin()
        loadKoinModules(listOf(
            module {
                single<Any>(qualifier = homeQualifier) { driverHomeRoute }
            }
        ))
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun go_to_active_screen() = runComposeUiTest {
        val koin = getKoin()
        val navController: XyzNavController = koin.get()
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                ProviderHomeScreen(navController)
            }
        }

        onNodeWithText("Start Taking Requests")
            .performClick()

        assertEquals(driverActiveRoute, navController.currentLocation.value)
    }
}
