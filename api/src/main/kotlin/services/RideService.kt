package org.pointyware.xyz.api.services

import org.pointyware.xyz.core.data.dtos.RideInfo
import java.sql.Connection

interface RideService {
    suspend fun getRideById(id: String): Result<RideInfo>
    suspend fun getRideCost(id: String): Result<Long>
}

class PostgresRideService(
    private val connection: Connection
): RideService {
    override suspend fun getRideById(id: String): Result<RideInfo> {
        return TODO("")
    }

    override suspend fun getRideCost(id: String): Result<Long> {
        return TODO("")
    }
}
