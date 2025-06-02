/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive.local

import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.ride.Ride
import kotlin.uuid.ExperimentalUuidApi

/**
 *
 */
interface RideCache {
    suspend fun saveRide(ride: Ride)
    suspend fun dropRide(ride: Ride)
}

@OptIn(ExperimentalUuidApi::class)
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
