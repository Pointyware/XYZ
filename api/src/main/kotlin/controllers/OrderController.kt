package org.pointyware.xyz.api.controllers

import org.pointyware.xyz.api.services.RideService

/**
 * Controls the market orders.
 */
interface OrderController {
    suspend fun bid(moneys: Float): Result<BidResponse>
    suspend fun ask(moneys: Float): Result<AskResponse>
    suspend fun getFairMarketValue(): Result<FairMarketValueResponse>
}

interface BidResponse
interface AskResponse
interface FairMarketValueResponse

class OrderControllerImpl(
    private val rideService: RideService
): OrderController {

    override suspend fun bid(moneys: Float): Result<BidResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun ask(moneys: Float): Result<AskResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getFairMarketValue(): Result<FairMarketValueResponse> {
        TODO("Not yet implemented")
    }
}
