package org.pointyware.xyz.api.controllers

import org.pointyware.xyz.api.services.RideService

/**
 * Controls the market orders.
 */
interface OrderController {
    suspend fun bid(cents: Long): Result<BidResponse>
    suspend fun ask(cents: Long): Result<AskResponse>
    suspend fun getFairMarketValue(): Result<FairMarketValueResponse>
}

interface BidResponse
interface AskResponse
interface FairMarketValueResponse

/**
 * Interacts with the Stripe API to create payment intents for completed rides.
 */
class OrderControllerImpl(
    private val rideService: RideService
): OrderController {

    override suspend fun bid(cents: Long): Result<BidResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun ask(cents: Long): Result<AskResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getFairMarketValue(): Result<FairMarketValueResponse> {
        TODO("Not yet implemented")
    }
}
