/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.data

import java.sql.Connection

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

class MarketRepositoryImpl(
    private val connection: Connection
): MarketRepository {
    override val bids: BidDao
        get() = TODO("Not yet implemented")
    override val asks: AskDao
        get() = TODO("Not yet implemented")
    override val matches: MatchDao
        get() = TODO("Not yet implemented")
}
