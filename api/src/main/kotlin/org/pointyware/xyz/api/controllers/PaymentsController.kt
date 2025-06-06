/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.controllers

import org.pointyware.xyz.api.services.PaymentsService
import org.pointyware.xyz.api.services.RideService

/**
 * A payments controller is responsible for handling payment-related operations. These consist of
 * initializing payments through intent objects, which can later be confirmed by the client.
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
 * Interacts with the [RideService] and [PaymentsService] to manage ride payment.
 */
class PaymentsControllerImpl(
    private val rideService: RideService,
    private val paymentsService: PaymentsService
): PaymentsController {

    override suspend fun getRideCost(rideId: String): Result<Long> {
        return rideService.getRideById(rideId).map { it.cost }
    }

    override suspend fun createPaymentIntent(rideId: String): Result<String> =
        rideService.getRideById(rideId) // ride may not exist
            .mapCatching {
                paymentsService.createPaymentIntent(it.riderId, it.cost).getOrThrow()
            }
}
