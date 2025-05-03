package org.pointyware.xyz.shared.navigation

import androidx.compose.runtime.Composable
import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.toTypedKey
import org.pointyware.xyz.ui.PaymentSheet

val paymentsRoute = "payment-sheet".toTypedKey<Unit>()

@Composable
fun XyzRootScope.payments() {

    location(paymentsRoute) {
        PaymentSheet()
    }
}
