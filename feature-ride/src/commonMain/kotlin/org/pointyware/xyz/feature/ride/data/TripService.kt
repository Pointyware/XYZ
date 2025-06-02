/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

import org.pointyware.xyz.core.entities.ride.Ride

/**
 * Defines actions that can be performed on a remote service to request rides.
 */
interface TripService {

    /**
     * Searches for destinations that match the given query.
     */
    suspend fun searchDestinations(query: String): Result<DestinationSearchResult>

    /**
     * Posts a ride to the service.
     */
    suspend fun postRide(ride: Ride): Result<Ride>
}

class TripServiceImpl : TripService {

    override suspend fun searchDestinations(query: String): Result<DestinationSearchResult> {
        TODO()
    }

    override suspend fun postRide(ride: Ride): Result<Ride> {
        TODO("Not yet implemented")
    }
}
