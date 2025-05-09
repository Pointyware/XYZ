package org.pointyware.xyz.api.services

import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams

/**
 *
 */
interface PaymentsService {
    fun createPaymentIntent(rideId: String, cost: Long): Result<String>
}

/**
 * Interacts with the Stripe API to create payment intents for completed rides.
 */
class StripeService(

): PaymentsService {

    init {
        Stripe.apiKey = System.getenv("STRIPE_API_KEY") ?: throw IllegalStateException("Stripe API key not set")
    }

    override fun createPaymentIntent(rideId: String, cost: Long): Result<String> = runCatching {
        val paymentIntentCreateParams = PaymentIntentCreateParams.builder()
            .setCustomer(rideId) // customer ID
            .setAmount(cost) // amount in cents
            .setCurrency("usd") // currency
            .build()

        val intent = PaymentIntent.create(paymentIntentCreateParams)

        intent.clientSecret
    }
}
