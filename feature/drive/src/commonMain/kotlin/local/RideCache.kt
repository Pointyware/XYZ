/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.local

import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.drive.data.RideSearchResult

/**
 *
 */
interface RideCache {
    suspend fun saveDestinations(query: String, searchResult: RideSearchResult)
    suspend fun getDestinations(query: String): RideSearchResult?
    suspend fun dropDestinations(query: String)
    suspend fun saveRide(ride: Ride)
    suspend fun dropRide(ride: Ride)
}

class RideCacheImpl(

): RideCache {

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

    private val rideCache = mutableMapOf<Uuid, Ride>()

    override suspend fun saveRide(ride: Ride) {
        rideCache[ride.id] = ride
    }

    override suspend fun dropRide(ride: Ride) {
        rideCache.remove(ride.id)
    }
}
