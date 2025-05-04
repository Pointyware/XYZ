/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.remote

import kotlinx.coroutines.flow.Flow
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.drive.RideFilter
import org.pointyware.xyz.drive.data.Cancellation
import org.pointyware.xyz.drive.entities.Request

/**
 * Defines the actions supported by the remote service.
 */
interface RideService {

    /**
     * Returns a flow of trip requests that match the given [filter].
     */
    suspend fun createRideFilter(filter: RideFilter): Result<Flow<List<Request>>>

    /**
     * Accepts the trip request with the given [requestId].
     */
    suspend fun acceptRequest(requestId: Uuid): Result<Ride>

    /**
     * Completes the active ride.
     */
    suspend fun completeRide(): Result<Ride>

    /**
     * Cancels the active ride.
     */
    suspend fun cancelRide(): Result<Cancellation>
}
