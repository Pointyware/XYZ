package org.pointyware.xyz.api.controllers

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
