/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.local

import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.ride.Ride

/**
 *
 */
interface RideCache {
    suspend fun saveRide(ride: Ride)
    suspend fun dropRide(ride: Ride)
}

class RideCacheImpl(

): RideCache {

    private val rideCache = mutableMapOf<Uuid, Ride>()

    override suspend fun saveRide(ride: Ride) {
        rideCache[ride.id] = ride
    }

    override suspend fun dropRide(ride: Ride) {
        rideCache.remove(ride.id)
    }
}
