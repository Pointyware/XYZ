package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.databases.RiderRepository
import org.pointyware.xyz.core.data.dtos.RideInfo

interface RideService {
    suspend fun getRideById(id: String): Result<RideInfo>
}

class RideServiceImpl(
    private val riderRepository: RiderRepository
): RideService {
    override suspend fun getRideById(id: String): Result<RideInfo> = runCatching {
        val ride = riderRepository.rides.getRideById(id)
        RideInfo(
            riderId = ride.id,
            cost = ride.cost
        )
    }
}
