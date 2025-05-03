package org.pointyware.xyz.shared.navigation

import org.pointyware.xyz.core.navigation.XyzRootScope
import org.pointyware.xyz.core.navigation.toTypedKey
import org.pointyware.xyz.feature.login.navigation.paymentsRoute

val paymentsRoute = "payment-sheet".toTypedKey<Unit>()

fun XyzRootScope.payments() {

    location(paymentsRoute) {

        val paymentSheet = rememberPaymentSheet(
            createIntentCallback = { paymentMethod, shouldSavePaymentMethod ->

                val clientSecret = "returned-from-our-server"
                CreateIntentResult.Success(clientSecret)
            },
            paymentResultCallback = { result ->
                when (result) {
                    PaymentSheetResult.Canceled -> {
                        TODO("Handle cancellation")
                    }
                    PaymentSheetResult.Completed -> {
                        TODO("Handle completion")
                    }
                    is PaymentSheetResult.Failed -> {
                        result.error.printStackTrace()
                    }
                }
            }
        )
    }
}
