/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.pointyware.xyz.drive.ui.CompanyProfileCreationView
import org.pointyware.xyz.drive.ui.CompanyProfileView
import kotlin.uuid.ExperimentalUuidApi

val companyCreationRoute = "company/create"
val companyViewingRoute = "company/{id}"

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
fun NavGraphBuilder.companyRouting(
    navController: NavHostController
) {

    composable(companyCreationRoute) {
        // TODO: replace with viewModel extension function that uses available (Koin)Scope
        CompanyProfileCreationView(
            state = TODO(),
            modifier = Modifier.fillMaxSize(),
            onBannerSelected = TODO(),
            onLogoSelected = TODO(),
            onNameChange = TODO(),
            onTaglineChange = TODO(),
            onDescriptionChange = TODO(),
            onPhoneNumberChange = TODO(),
            onDriverAdd = TODO(),
            onDriverRemove = TODO(),
            onContinue = TODO(),
        )
    }

    composable(companyViewingRoute) {

        CompanyProfileView(
            state = TODO(),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
