package org.pointyware.xyz.api.services

import org.pointyware.xyz.core.data.dtos.RideInfo

interface RideService {
    suspend fun getRideById(id: String): Result<RideInfo>
    suspend fun getRideCost(id: String): Result<Long>
}

class PostgresRideService(
    private val dbConnection: Any // TODO: Replace with actual DB connection type
): RideService {
    override suspend fun getRideById(id: String): Result<RideInfo> {
        return TODO("")
    }

    override suspend fun getRideCost(id: String): Result<Long> {
        return TODO("")
    }
}
