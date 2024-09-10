/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.remote

import kotlinx.coroutines.flow.Flow
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.entities.ride.RideFilter

/**
 * Defines the actions supported by the remote service.
 */
interface RideService {

    /**
     * Cancels a ride.
     */
    suspend fun cancelRide(ride: Ride): Result<Ride>

    /**
     * Completes a ride.
     */
    suspend fun completeRide(ride: Ride): Result<Ride>

    /**
     * Creates a filter for rides that match the given criteria.
     */
    suspend fun createRideFilter(filter: RideFilter): Result<Flow<Ride>>
}
