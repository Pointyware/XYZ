/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

/**
 * Defines actions that can be performed on a remote service to request rides.
 */
interface RideRequestService {

    /**
     * Searches for destinations that match the given query.
     */
    suspend fun searchDestinations(query: String): Result<RideSearchResult>
}

class RideRequestServiceImpl : RideRequestService {

    override suspend fun searchDestinations(query: String): Result<RideSearchResult> {
        TODO()
    }
}
