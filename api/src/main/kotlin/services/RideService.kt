package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.databases.RideDatabase
import org.pointyware.xyz.core.data.dtos.RideInfo

interface RideService {
    suspend fun getRideById(id: String): Result<RideInfo>
    suspend fun getRideCost(id: String): Result<Long>
}

class RideServiceImpl(
    private val rideDatabase: RideDatabase
): RideService {
    override suspend fun getRideById(id: String): Result<RideInfo> {
        return TODO("")
    }

    override suspend fun getRideCost(id: String): Result<Long> {
        return TODO("")
    }
}
