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
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel
import org.pointyware.xyz.feature.ride.ui.RiderProfileCreationView

/**
 *
 */
@Composable
fun RiderProfileCreationScreen(
    viewModel: RiderProfileCreationViewModel,
    navController: XyzNavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    RiderProfileCreationView(
        state = state,
        modifier = Modifier.fillMaxSize(),
        onProfileImageSelected = TODO(),
        onGivenNameChange = TODO(),
        onMiddleNameChange = TODO(),
        onFamilyNameChange = TODO(),
        onBirthdateSelected = TODO(),
        onGenderSelected = TODO(),
        onDisabilitiesSelected = TODO(),
        onPreferencesChange = TODO(),
        onSubmit = TODO(),
    )
}
