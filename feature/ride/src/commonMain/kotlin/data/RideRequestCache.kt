/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

/**
 *
 */
interface RideRequestCache {
    suspend fun saveDestinations(query: String, searchResult: RideSearchResult)
    suspend fun getDestinations(query: String): RideSearchResult?
    suspend fun dropDestinations(query: String)
}

class RideRequestCacheImpl(

): RideRequestCache {

    private val queryCache = mutableMapOf<String, RideSearchResult>()

    override suspend fun saveDestinations(query: String, searchResult: RideSearchResult) {
        queryCache[query] = searchResult
    }

    override suspend fun getDestinations(query: String): RideSearchResult? {
        return queryCache[query]
    }

    override suspend fun dropDestinations(query: String) {
        queryCache.remove(query)
    }
}
