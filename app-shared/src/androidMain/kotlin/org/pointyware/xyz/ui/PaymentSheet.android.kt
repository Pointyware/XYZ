package org.pointyware.xyz.ui

import androidx.compose.runtime.Composable
import com.stripe.android.paymentsheet.CreateIntentResult
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet

@Composable
actual fun PaymentSheet() {
    val paymentSheet = rememberPaymentSheet(
        createIntentCallback = { paymentMethod, shouldSavePaymentMethod ->
            // TODO: Call your backend to create a PaymentIntent and return the client secret

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
