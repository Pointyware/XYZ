/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.pointyware.xyz.drive.data.DriverSettingsRepository
import org.pointyware.xyz.drive.data.ProviderTripRepository

/**
 *
 */
class WatchRatedRequests(
    private val repository: ProviderTripRepository,
    private val driverSettingsRepository: DriverSettingsRepository
) {

    suspend fun invoke(): Result<Flow<List<EstimatedRequest>>> {
        val filter = driverSettingsRepository.getFilter()
        val rates = driverSettingsRepository.getDriverRates()
        val location = driverSettingsRepository.getDriverLocation()

        repository.watchRequests(filter)
            .onSuccess { flow ->
                val estimatedFlow = flow.map { request ->
                    request.map {
                        EstimatedRequest(
                            it,
                            location.distanceTo(it.route.start.coordinates),
                            rates
                        )
                    }
                }
                return Result.success(estimatedFlow)
            }
            .onFailure {
                return Result.failure(it)
            }
        throw IllegalStateException("Unreachable code")
    }
}
