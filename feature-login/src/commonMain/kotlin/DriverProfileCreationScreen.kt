/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.feature.login.navigation.companyCreationRoute
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationView
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel

/**
 * Binds a [DriverProfileCreationViewModel] to the [DriverProfileCreationView].
 */
@Composable
fun DriverProfileCreationScreen(
    viewModel: DriverProfileCreationViewModel,
    navController: XyzNavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    DriverProfileCreationView(
        state = state,
        modifier = modifier.fillMaxSize(),
        onProfileImageSelected = viewModel.profileCreationViewModel::onProfileImageSelected,
        onGivenNameChange = viewModel.profileCreationViewModel::onGivenNameChange,
        onMiddleNameChange = viewModel.profileCreationViewModel::onMiddleNameChange,
        onFamilyNameChange = viewModel.profileCreationViewModel::onFamilyNameChange,
        onBirthdateSelected = viewModel.profileCreationViewModel::onBirthdateSelected,
        onGenderSelected = viewModel.profileCreationViewModel::onGenderSelected,
        onAccommodationsSelected = viewModel::onAccommodationsSelected,
        onCompanySearchChange = viewModel::onCompanySearchChange,
        onCreateCompany = { navController.navigateTo(companyCreationRoute) },
        onCompanySelected = viewModel::onCompanySelected,
        onSubmit = viewModel::onSubmit,
    )
}
