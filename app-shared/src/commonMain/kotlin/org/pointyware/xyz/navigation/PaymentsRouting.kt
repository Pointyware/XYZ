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
