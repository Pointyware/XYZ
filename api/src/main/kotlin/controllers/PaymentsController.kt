package org.pointyware.xyz.api.controllers

import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import org.pointyware.xyz.api.services.RideService

/**
 *
 */
interface PaymentsController {
    suspend fun getRideCost(rideId: String): Result<Long>

    /**
     * Creates a payment intent for the given ride ID.
     * @return The client secret of the payment intent.
     */
    suspend fun createPaymentIntent(rideId: String): Result<String>
}

/**
 *
 */
class StripePaymentsController(
    val rideService: RideService
): PaymentsController {

    override suspend fun getRideCost(rideId: String): Result<Long> {
        return rideService.getRideCost(rideId)
    }

    override suspend fun createPaymentIntent(rideId: String): Result<String> {
        return rideService.getRideById(rideId)
            .map {
                val paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                    .setCustomer(it.riderId) // customer ID
                    .setAmount(it.cost) // amount in cents
                    .setCurrency("usd") // currency
                    .build()

                val intent = PaymentIntent.create(paymentIntentCreateParams)

                intent.clientSecret
            }
    }
}
