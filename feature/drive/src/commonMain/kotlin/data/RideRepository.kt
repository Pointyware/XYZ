/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.data

import kotlinx.coroutines.flow.Flow
import org.pointyware.xyz.core.entities.Ride
import org.pointyware.xyz.drive.local.RideDao
import org.pointyware.xyz.drive.remote.RideService


/**
 * This repository serves as the access point to the ride data. It mediates between a local cache
 * and a remote service.
 */
interface RideRepository {
    suspend fun searchDestinations(query: String): List<String>
    suspend fun postRide(ride: Ride): Result<Ride>
    suspend fun cancelRide(ride: Ride): Result<Ride>
    suspend fun createRideFilter(criteria: Ride.Criteria): Result<Flow<Ride>>
}

class RideRepositoryImpl(
    private val rideService: RideService,
    private val rideDao: RideDao,
): RideRepository {
    override suspend fun searchDestinations(query: String): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun postRide(ride: Ride): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun cancelRide(ride: Ride): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun createRideFilter(criteria: Ride.Criteria): Result<Flow<Ride>> {
        TODO("Not yet implemented")
    }
}
