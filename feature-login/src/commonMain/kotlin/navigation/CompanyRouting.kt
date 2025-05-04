/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.toTypedKey
import org.pointyware.xyz.drive.ui.CompanyProfileCreationView
import org.pointyware.xyz.drive.ui.CompanyProfileView

val companyCreationRoute = "company/create".toTypedKey<Unit>()
val companyViewingRoute = "company/{id}".toTypedKey<Uuid>()

/**
 *
 */
@Composable
fun XyzRootScope.companyRouting(
    navController: XyzNavController
) {

    location(companyCreationRoute) {
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

    location(companyViewingRoute) {

        CompanyProfileView(
            state = TODO(),
            modifier = Modifier.fillMaxSize(),
        )
    }
}
