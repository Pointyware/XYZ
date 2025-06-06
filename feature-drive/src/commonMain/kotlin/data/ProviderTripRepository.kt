/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
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
 import org.pointyware.xyz.core.entities.profile.DriverProfile
 import org.pointyware.xyz.core.entities.ride.ActiveRide
 import org.pointyware.xyz.core.entities.ride.CompletedRide
 import org.pointyware.xyz.core.entities.ride.CompletingRide
 import org.pointyware.xyz.core.entities.ride.PendingRide
 import org.pointyware.xyz.core.entities.ride.Ride
 import org.pointyware.xyz.core.entities.ride.planRide
 import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.LocationService
 import org.pointyware.xyz.drive.RideFilter
 import org.pointyware.xyz.drive.entities.Request
 import org.pointyware.xyz.drive.local.RideCache
 import org.pointyware.xyz.drive.remote.RideService
 import kotlin.uuid.ExperimentalUuidApi
 import kotlin.uuid.Uuid


/**
 * This repository serves as the access point to ride data from the driver perspective.
 * It mediates between a local cache and a remote service.
 */
@OptIn(ExperimentalUuidApi::class)
interface ProviderTripRepository {
    /**
     * Watch for new trip requests that match the given [filter].
     */
    suspend fun watchRequests(filter: RideFilter): Result<Flow<List<Request>>>

    /**
     * Accept the trip request with the given [requestId].
     */
    suspend fun acceptRequest(requestId: Uuid): Result<PendingRide>

    /**
     * Reject the trip request with the given [requestId].
     */
    suspend fun rejectRequest(requestId: Uuid): Result<Unit>

    /**
     * Pick up the rider for the active ride.
     */
    suspend fun pickUpRider(): Result<ActiveRide>

    /**
     * Complete the active ride.
     */
    suspend fun completeRide(): Result<CompletedRide>

    /**
     * Cancel the active ride.
     */
    suspend fun cancelRide(): Result<Cancellation>
}

data class Cancellation(
    val ride: Ride,
    val reason: String,
)

@OptIn(ExperimentalUuidApi::class)
class ProviderTripRepositoryImpl(
    private val locationService: LocationService,
    private val rideService: RideService,
    private val rideCache: RideCache,
): ProviderTripRepository {

    override suspend fun watchRequests(filter: RideFilter): Result<Flow<List<Request>>> {
        return rideService.createRideFilter(filter)
    }

    override suspend fun acceptRequest(requestId: Uuid): Result<PendingRide> {
        TODO("Not yet implemented")
    }

    override suspend fun rejectRequest(requestId: Uuid): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun pickUpRider(): Result<ActiveRide> {
        TODO("Not yet implemented")
    }

    override suspend fun completeRide(): Result<CompletedRide> {
        TODO("Not yet implemented")
    }

    override suspend fun cancelRide(): Result<Cancellation> {
        TODO("Not yet implemented")
    }
}

@ExperimentalUuidApi
class TestProviderTripRepository(
    private val driver: DriverProfile,
    private val locationService: LocationService,
    private val dataScope: CoroutineScope,
): ProviderTripRepository {

    private val mutableNewRequests = MutableSharedFlow<Request>()
    private val mutableActiveRequests: MutableStateFlow<Set<Request>> = MutableStateFlow(emptySet())

    private var activeRide: Ride? = null

    override suspend fun watchRequests(filter: RideFilter): Result<Flow<List<Request>>> {
        return Result.success(mutableActiveRequests.map { list -> list.filter(filter::accepts)})
    }

    override suspend fun acceptRequest(requestId: Uuid): Result<PendingRide> {
        val request = mutableActiveRequests.value.find { it.rideId == requestId }
        return if (request != null) {
            mutableActiveRequests.update { it - request }
            val plannedRide = planRide(
                id = request.rideId,
                rider = request.rider,
                plannedRoute = request.route,
                timePosted = request.timePosted
            )
            val pendingRide = plannedRide.accept(
                driver = driver,
                timeAccepted = Clock.System.now()
            )
            activeRide = pendingRide
            Result.success(pendingRide)
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

    override suspend fun pickUpRider(): Result<ActiveRide> {
        activeRide?.let {
            if (it is PendingRide) {
                val activeRide = it.arrive(Clock.System.now())
                this.activeRide = activeRide
                return Result.success(activeRide)
            } else {
                return Result.failure(IllegalStateException("Current trip is not pending"))
            }
        } ?: return Result.failure(IllegalStateException("No current trip to pick up passenger"))
    }

    override suspend fun completeRide(): Result<CompletedRide> {
        activeRide?.let {
            activeRide = null
            when (it) {
                is ActiveRide -> {
                    return Result.success(it.complete(Clock.System.now()))
                }
                is CompletingRide -> {
                    return Result.success(it.complete(Clock.System.now()))
                }
                else -> {
                    return Result.failure(IllegalStateException("Current trip is not active or completing"))
                }
            }
        } ?: return Result.failure(IllegalStateException("No current trip to complete"))
    }

    override suspend fun cancelRide(): Result<Cancellation> {
        return activeRide?.let {
            activeRide = null
            Result.success(Cancellation(it, "Provider canceled"))
        } ?: Result.failure(IllegalStateException("No current trip to cancel"))
    }

    fun addRequest(testRequest: Request) {
        dataScope.launch {
            mutableNewRequests.emit(testRequest)
            mutableActiveRequests.update { it + testRequest }
        }
    }
}
