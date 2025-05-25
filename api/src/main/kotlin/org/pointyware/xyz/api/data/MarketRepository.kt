package org.pointyware.xyz.api.data

/**
 * Repository interface for accessing market data.
 */
interface MarketRepository {
    val bids: BidDao
    val asks: AskDao
    val matches: MatchDao
}

/**
 *
 */
interface BidDao {

}

/**
 *
 */
interface AskDao {

}

/**
 *
 */
interface MatchDao {

}
