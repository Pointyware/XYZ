/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.drive.ui.DriverProfileCreationView
import org.pointyware.xyz.feature.ride.ui.RiderProfileCreationView

/**
 * The main screen for creating a new user account. First determines if the user intends to use
 * the app as a rider or driver, then guides them through next steps to create their profile.
 */
@Composable
fun AccountCreationScreen(
    modifier: Modifier = Modifier,
) {
    // TODO: Setup animation/navigation between the different views
    RoleSelectionView()
    DriverProfileCreationView()
    RiderProfileCreationView()
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
