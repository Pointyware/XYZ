package org.pointyware.xyz.shared.navigation

import androidx.compose.runtime.Composable
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
