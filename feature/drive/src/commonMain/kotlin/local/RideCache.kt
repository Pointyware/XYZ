/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.local

import kotlinx.coroutines.flow.Flow
import org.pointyware.xyz.core.entities.Ride
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
    suspend fun createRideFilter(criteria: Ride.Criteria): Result<Flow<Ride>>
}

class RideCacheImpl(
    private val rideDao: RideDao
): RideCache {
    override suspend fun saveDestinations(query: String, searchResult: RideSearchResult) {
        TODO("Not yet implemented")
    }

    override suspend fun getDestinations(query: String): RideSearchResult? {
        TODO("Not yet implemented")
    }

    override suspend fun dropDestinations(query: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveRide(ride: Ride) {
        TODO("Not yet implemented")
    }

    override suspend fun dropRide(ride: Ride) {
        TODO("Not yet implemented")
    }

    override suspend fun createRideFilter(criteria: Ride.Criteria): Result<Flow<Ride>> {
        TODO("Not yet implemented")
    }
}
