package org.pointyware.xyz.api.services

import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams

/**
 * A payments service is responsible for handling payment-related operations. These consist of
 * initializing payments through intent objects, which can later be confirmed by the client.
 */
interface PaymentsService {
    /**
     * @param customerId The ID of the customer to create the payment intent for.
     * @param cost The cost of the ride in cents.
     */
    fun createPaymentIntent(customerId: String, cost: Long): Result<String>
}

/**
 * Interacts with the Stripe API to create and process payment intents.
 */
class StripePaymentsService(

): PaymentsService {

    init {
        Stripe.apiKey = System.getenv("STRIPE_API_KEY") ?: throw IllegalStateException("Stripe API key not set")
    }

    override fun createPaymentIntent(customerId: String, cost: Long): Result<String> = runCatching {
        val paymentIntentCreateParams = PaymentIntentCreateParams.builder()
            .setCustomer(customerId) // customer ID
            .setAmount(cost) // amount in cents
            .setCurrency("usd") // currency
            .build()

        val intent = PaymentIntent.create(paymentIntentCreateParams)

        intent.clientSecret
    }
}
