/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.drive.RideFilter
import org.pointyware.xyz.drive.entities.Request
import org.pointyware.xyz.drive.local.RideCache
import org.pointyware.xyz.drive.remote.RideService


/**
 * This repository serves as the access point to ride data from the driver perspective.
 * It mediates between a local cache and a remote service.
 */
interface RideRepository {
    /**
     * Watch for new ride requests that match the given [filter].
     */
    suspend fun watchRequests(filter: RideFilter): Result<Flow<Request>>

    /**
     * Accept the ride request with the given [requestId].
     */
    suspend fun acceptRequest(requestId: Uuid): Result<Ride>

    /**
     * Complete the active ride.
     */
    suspend fun completeRide(): Result<Ride>

    /**
     * Cancel the active ride.
     */
    suspend fun cancelRide(): Result<Cancellation>
}

data class Cancellation(
    val ride: Ride,
    val reason: String,
)

class RideRepositoryImpl(
    private val rideService: RideService,
    private val rideCache: RideCache,
): RideRepository {

    override suspend fun watchRequests(filter: RideFilter): Result<Flow<Request>> {
        return rideService.createRideFilter(filter)
    }

    override suspend fun acceptRequest(requestId: Uuid): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun completeRide(): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun cancelRide(): Result<Cancellation> {
        TODO("Not yet implemented")
    }
}

class TestRideRepository(
    val dataScope: CoroutineScope,
): RideRepository {

    private val mutableNewRequests = MutableSharedFlow<Request>()
    private val mutableActiveRequests: MutableStateFlow<Set<Request>> = MutableStateFlow(emptySet())

    private var activeRide: Ride? = null

    override suspend fun watchRequests(filter: RideFilter): Result<Flow<Request>> {
        return Result.success(mutableNewRequests.filter { filter.accepts(it) })
    }

    override suspend fun acceptRequest(requestId: Uuid): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun completeRide(): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun cancelRide(): Result<Cancellation> {
        return activeRide?.let {
            activeRide = null
            Result.success(Cancellation(it, "Driver canceled"))
        } ?: Result.failure(IllegalStateException("No active ride to cancel"))
    }
}
