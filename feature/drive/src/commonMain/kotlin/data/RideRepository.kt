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
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.entities.ride.RideFilter
import org.pointyware.xyz.drive.local.RideCache
import org.pointyware.xyz.drive.remote.RideService


/**
 * This repository serves as the access point to ride data from the driver perspective.
 * It mediates between a local cache and a remote service.
 */
interface RideRepository {
    suspend fun cancelRide(ride: Ride): Result<Ride>
    suspend fun watchRides(filter: RideFilter): Result<Flow<Ride>>
}

class RideRepositoryImpl(
    private val rideService: RideService,
    private val rideCache: RideCache,
): RideRepository {

    override suspend fun cancelRide(ride: Ride): Result<Ride> {
        return rideService.cancelRide(ride)
            .onSuccess {
                rideCache.dropRide(ride)
            }
    }

    override suspend fun watchRides(filter: RideFilter): Result<Flow<Ride>> {
        return rideService.createRideFilter(filter)
    }
}

class TestRideRepository(
    val dataScope: CoroutineScope,
): RideRepository {

    private val mutableNewRides = MutableSharedFlow<Ride>()
    private val mutablePostedRides: MutableStateFlow<Set<Ride>> = MutableStateFlow(emptySet())

    override suspend fun cancelRide(ride: Ride): Result<Ride> {
        mutablePostedRides.update { it - ride }
        return Result.success(ride)
    }

    override suspend fun watchRides(filter: RideFilter): Result<Flow<Ride>> {
        return Result.success(mutableNewRides.filter { filter.accepts(it) })
    }

    fun postRide(ride: Ride) {
        mutableNewRides.tryEmit(ride)
        mutablePostedRides.update { it + ride }
    }
}
