/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.app.drive

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onSibling
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.navigation.StackNavigationController
import org.pointyware.xyz.core.remote.di.coreRemoteModule
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.feature.login.DriverProfileCreationScreen
import org.pointyware.xyz.feature.login.data.CompanyRepository
import org.pointyware.xyz.feature.login.data.ProfileRepository
import org.pointyware.xyz.feature.login.data.TestCompanyRepository
import org.pointyware.xyz.feature.login.data.TestProfileRepository
import org.pointyware.xyz.feature.login.di.profileDataModule
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel
import org.pointyware.xyz.shared.di.appModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * System/UI Test for Driver Profile Creation View
 */
@OptIn(ExperimentalTestApi::class)
class DriverProfileCreationUiTest {

    private lateinit var profileRepository: TestProfileRepository
    private lateinit var companyRepository: TestCompanyRepository

    private fun testFeatureProfileModule(
        profileRepository: ProfileRepository,
        companyRepository: CompanyRepository
    ) = module {
        single<ProfileRepository> { profileRepository }
        single<CompanyRepository> { companyRepository }
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
        unloadKoinModules(listOf(
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
    fun create_driver_profile() = runComposeUiTest {
        val di = getKoin()
        val viewModel = di.get<DriverProfileCreationViewModel>()
        val navController = di.get<StackNavigationController<Any, Any?>>()

        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                DriverProfileCreationScreen(
                    viewModel,
                    navController
                )
            }
        }

        /*
        When:
        - User Enters <given name>
        Then:
        - Create Profile button is disabled
         */
        onNodeWithText("Given Name")
            .assertExists()
            .performTextInput("John")
        onNodeWithText("Submit")
            .assertExists()
            .assertIsNotEnabled()

        /*
        When:
        - User Enters <middle name>
        Then:
        - Create Profile button is disabled
         */
        onNodeWithText("Middle Name")
            .assertExists()
            .performTextInput("Jacob")
        onNodeWithText("Submit")
            .assertExists()
            .assertIsNotEnabled()

        /*
        When:
        - User Enters <family name>
        Then:
        - Create Profile button is enabled
         */
        onNodeWithText("Family Name")
            .assertExists()
            .performTextInput("Jingleheimer Schmidt")
        onNodeWithText("Submit")
            .assertExists()
            .assertIsEnabled()

        /*
        When:
        - User Enters <birthdate>
        Then:
        - Create Profile button is disabled
         */
        // TODO: Implement date picker

        /*
        When:
        - User Selects <gender>
        Then:
        - Create Profile button is enabled
         */
        // TODO: Implement gender picker

        /*
        When:
        - User Taps "WheelchairAccess"
        Then:
        - WheelchairAccess is added to list of accommodations
         */
        onNodeWithText("WheelchairAccess")
            .assertExists()
            .performClick()
        onNodeWithText("WheelchairAccess")
            .assertExists()
            .onSibling().performClick()

        /*
        When:
        - User Taps "Create Profile"
        Then:
        - Driver Profile is created
          - Profile contains <given name>, <middle name>, <family name>, <birthdate>, <gender>
          - Profile contains <accommodation>
          - Profile company is "Independent"
         */
        onNodeWithText("Submit")
            .assertExists()
            .performClick()
    }
}
