/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.navigation.XyzNavController
import org.pointyware.xyz.drive.ui.DriverProfileCreationView
import org.pointyware.xyz.feature.login.viewmodels.AccountCreationStep
import org.pointyware.xyz.feature.login.viewmodels.AccountCreationViewModel
import org.pointyware.xyz.feature.ride.ui.RiderProfileCreationView

/**
 * The main screen for creating a new user account. First determines if the user intends to use
 * the app as a rider or driver, then guides them through next steps to create their profile.
 */
@Composable
fun AccountCreationScreen(
    viewModel: AccountCreationViewModel,
    navController: XyzNavController,
    modifier: Modifier = Modifier,
) {
    val creationStep = viewModel.creationStep.value
    AnimatedContent(
        targetState = creationStep,
        modifier = modifier.fillMaxSize(),
    ) {
        when (creationStep) {
            AccountCreationStep.Role -> RoleSelectionView()
            AccountCreationStep.Rider -> DriverProfileCreationView()
            AccountCreationStep.Driver -> RiderProfileCreationView()
            AccountCreationStep.Confirm -> TODO("Not yet implemented")
        }
    }
}

@Composable
fun RoleSelectionView(
    modifier: Modifier = Modifier,
) {
    /*
    TODO: Implement the RoleSelectionView. This view should allow the user to select their role.
      1. Greeting
      2. Ride Button
      3. Drive Button
      4. Continue Button
     */
}
