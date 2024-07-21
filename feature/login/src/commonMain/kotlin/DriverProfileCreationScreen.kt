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
import org.pointyware.xyz.drive.ui.DriverProfileCreationView
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel

/**
 *
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
        onProfileImageSelected = viewModel::onProfileImageSelected,
        onGivenNameChange = viewModel::onGivenNameChange,
        onMiddleNameChange = viewModel::onMiddleNameChange,
        onFamilyNameChange = viewModel::onFamilyNameChange,
        onBirthdateSelected = viewModel::onBirthdateSelected,
        onGenderSelected = viewModel::onGenderSelected,
        onCompanySelected = viewModel::onCompanySelected,
        onSubmit = viewModel::onSubmit,
    )
}
