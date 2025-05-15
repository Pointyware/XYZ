package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.databases.RideDatabase
import org.pointyware.xyz.core.data.dtos.RideInfo

interface RideService {
    suspend fun getRideById(id: String): Result<RideInfo>
}

class RideServiceImpl(
    private val rideDatabase: RideDatabase
): RideService {
    override suspend fun getRideById(id: String): Result<RideInfo> = runCatching {
        val ride = rideDatabase.rides.getRideById(id)
        RideInfo(
            riderId = ride.id,
            cost = ride.cost
        )
    }
}
