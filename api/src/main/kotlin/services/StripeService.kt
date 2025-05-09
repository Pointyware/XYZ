package org.pointyware.xyz.api.services

import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams

class StripeService {
    fun createPaymentIntent(rideId: String, cost: Long): Result<String> = runCatching {
        val paymentIntentCreateParams = PaymentIntentCreateParams.builder()
            .setCustomer(rideId) // customer ID
            .setAmount(cost) // amount in cents
            .setCurrency("usd") // currency
            .build()

        val intent = PaymentIntent.create(paymentIntentCreateParams)

        intent.clientSecret
    }
}
