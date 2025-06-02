/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.pointyware.xyz.ui.PaymentSheet

val paymentsRoute = "payment-sheet"

fun NavGraphBuilder.payments(
    navController: NavHostController
) {
    composable(paymentsRoute) {
        PaymentSheet()
    }
}
