/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.data.CommonRepository
import org.pointyware.xyz.api.dtos.RideInfo

interface RideService {
    suspend fun getRideById(id: String): Result<RideInfo>
}

class RideServiceImpl(
    private val commonRepository: CommonRepository
): RideService {
    override suspend fun getRideById(id: String): Result<RideInfo> = runCatching {
        val ride = commonRepository.rides.getRideById(id)
        RideInfo(
            riderId = ride.id,
            cost = ride.cost
        )
    }
}
