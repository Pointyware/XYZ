/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.pointyware.xyz.ui.LoadingResultView
import org.pointyware.xyz.feature.login.ride.ui.RiderProfileCreationView
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel

/**
 *
 */
@Composable
fun RiderProfileCreationScreen(
    viewModel: RiderProfileCreationViewModel,
    navController: NavHostController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val state by viewModel.state.collectAsState()
        val loadingState by viewModel.loadingState.collectAsState()
        RiderProfileCreationView(
            state = state,
            modifier = Modifier.align(Alignment.Center),
            onProfileImageSelected = viewModel.profileCreationViewModel::onProfileImageSelected,
            onGivenNameChange = viewModel.profileCreationViewModel::onGivenNameChange,
            onMiddleNameChange = viewModel.profileCreationViewModel::onMiddleNameChange,
            onFamilyNameChange = viewModel.profileCreationViewModel::onFamilyNameChange,
            onBirthdateSelected = viewModel.profileCreationViewModel::onBirthdateSelected,
            onGenderSelected = viewModel.profileCreationViewModel::onGenderSelected,
            onDisabilitiesSelected = viewModel::onDisabilitiesSelected,
            onPreferencesChange = viewModel::onPreferencesChange,
            onSubmit = viewModel::onSubmit,
        )
        LoadingResultView(
            state = loadingState,
            onSuccess = {},
            onDismiss = viewModel::clearLoadingError
        )
    }
}
