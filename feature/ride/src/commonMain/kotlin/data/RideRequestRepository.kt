/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.entities.ride.Route

/**
 *
 */
interface RideRequestRepository {
    suspend fun requestRide(route: Route): Result<Ride>
    suspend fun scheduleRide(route: Route, time: Instant): Result<Ride>
}

/**
 *
 */
class RideRequestRepositoryImpl(
//    private val cache: RideRequestCache,
//    private val service: RideRequestService,
): RideRequestRepository {
    override suspend fun requestRide(route: Route): Result<Ride> {
        TODO("")
    }

    override suspend fun scheduleRide(route: Route, time: Instant): Result<Ride> {
        TODO("Not yet implemented")
    }
}

/**
 *
 */
class TestRideRequestRepository: RideRequestRepository {
    override suspend fun requestRide(route: Route): Result<Ride> {
        TODO("")
    }

    override suspend fun scheduleRide(route: Route, time: Instant): Result<Ride> {
        TODO("Not yet implemented")
    }
}
