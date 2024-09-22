/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.entities.ride.activeRide
import org.pointyware.xyz.drive.RideFilter
import org.pointyware.xyz.drive.entities.Request
import org.pointyware.xyz.drive.local.RideCache
import org.pointyware.xyz.drive.remote.RideService


/**
 * This repository serves as the access point to ride data from the driver perspective.
 * It mediates between a local cache and a remote service.
 */
interface ProviderTripRepository {
    /**
     * Watch for new trip requests that match the given [filter].
     */
    suspend fun watchRequests(filter: RideFilter): Result<Flow<List<Request>>>

    /**
     * Accept the trip request with the given [requestId].
     */
    suspend fun acceptRequest(requestId: Uuid): Result<Ride>

    /**
     * Reject the trip request with the given [requestId].
     */
    suspend fun rejectRequest(requestId: Uuid): Result<Unit>

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

class ProviderTripRepositoryImpl(
    private val rideService: RideService,
    private val rideCache: RideCache,
): ProviderTripRepository {

    override suspend fun watchRequests(filter: RideFilter): Result<Flow<List<Request>>> {
        return rideService.createRideFilter(filter)
    }

    override suspend fun acceptRequest(requestId: Uuid): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun rejectRequest(requestId: Uuid): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun completeRide(): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun cancelRide(): Result<Cancellation> {
        TODO("Not yet implemented")
    }
}

class TestProviderTripRepository(
    val dataScope: CoroutineScope,
): ProviderTripRepository {

    private val mutableNewRequests = MutableSharedFlow<Request>()
    private val mutableActiveRequests: MutableStateFlow<Set<Request>> = MutableStateFlow(emptySet())

    private var activeRide: Ride? = null

    override suspend fun watchRequests(filter: RideFilter): Result<Flow<List<Request>>> {
        return Result.success(mutableActiveRequests.map { list -> list.filter(filter::accepts)})
    }

    override suspend fun acceptRequest(requestId: Uuid): Result<Ride> {
        val request = mutableActiveRequests.value.find { it.rideId == requestId }
        return if (request != null) {
            mutableActiveRequests.update { it - request }
            val newRide = activeRide(
                id = request.rideId,
                rider = request.rider,
                plannedRoute = request.route,
                timePosted = request.timePosted,
                timeAccepted = Clock.System.now()
            )
            Result.success(newRide)
        } else {
            Result.failure(IllegalStateException("Request not found"))
        }
    }

    override suspend fun rejectRequest(requestId: Uuid): Result<Unit> {
        val request = mutableActiveRequests.value.find { it.rideId == requestId }
        return if (request != null) {
            mutableActiveRequests.update { it - request }
            Result.success(Unit)
        } else {
            Result.failure(IllegalStateException("Request not found"))
        }
    }

    override suspend fun completeRide(): Result<Ride> {
        activeRide?.let {
            activeRide = null
            return Result.success(it)
        } ?: return Result.failure(IllegalStateException("No active ride to complete"))
    }

    override suspend fun cancelRide(): Result<Cancellation> {
        return activeRide?.let {
            activeRide = null
            Result.success(Cancellation(it, "Driver canceled"))
        } ?: Result.failure(IllegalStateException("No active ride to cancel"))
    }

    fun addRequest(testRequest: Request) {
        dataScope.launch {
            mutableNewRequests.emit(testRequest)
            mutableActiveRequests.update { it + testRequest }
        }
    }
}
