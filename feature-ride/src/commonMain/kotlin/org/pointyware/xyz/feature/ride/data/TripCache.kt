/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

/**
 *
 */
interface TripCache {
    suspend fun saveDestinations(query: String, searchResult: DestinationSearchResult)
    suspend fun getDestinations(query: String): DestinationSearchResult?
    suspend fun dropDestinations(query: String)
}

class TripCacheImpl(

): TripCache {

    private val queryCache = mutableMapOf<String, DestinationSearchResult>()

    override suspend fun saveDestinations(query: String, searchResult: DestinationSearchResult) {
        queryCache[query] = searchResult
    }

    override suspend fun getDestinations(query: String): DestinationSearchResult? {
        return queryCache[query]
    }

    override suspend fun dropDestinations(query: String) {
        queryCache.remove(query)
    }
}
