/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.controllers

import org.pointyware.xyz.api.services.RideServiceImpl
import org.pointyware.xyz.core.entities.ride.Ride

/**
 * Once a bid/ask has been settled through the [OrderController], the ride controller manages
 * the state and lifecycles of the ride.
 */
interface RideController {
    suspend fun startRide(): Result<Ride>
    suspend fun stopRide(): Result<Ride>
    suspend fun getRideStatus(): Result<Ride>
}

/**
 *
 */
class RideControllerImpl(
    private val rideService: RideServiceImpl
): RideController {

    /**
     * Called by driver to start a ride.
     */
    override suspend fun startRide(): Result<Ride> {
        TODO("Not yet implemented")
    }

    /**
     *
     */
    override suspend fun stopRide(): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun getRideStatus(): Result<Ride> {
        TODO("Not yet implemented")
    }
}
