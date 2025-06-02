/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.services

import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import org.pointyware.xyz.api.BuildConfig

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
        Stripe.apiKey = BuildConfig.STRIPE_API_KEY
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
