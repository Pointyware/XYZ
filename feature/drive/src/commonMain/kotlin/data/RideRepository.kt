/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.data

import kotlinx.coroutines.flow.Flow
import org.pointyware.xyz.core.entities.ride.Location
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.entities.ride.RideFilter
import org.pointyware.xyz.drive.local.RideCache
import org.pointyware.xyz.drive.remote.RideService


/**
 * This repository serves as the access point to the ride data. It mediates between a local cache
 * and a remote service.
 */
interface RideRepository {
    suspend fun searchDestinations(query: String): Result<RideSearchResult>
    suspend fun postRide(ride: Ride): Result<Ride>
    suspend fun cancelRide(ride: Ride): Result<Ride>
    suspend fun watchRides(filter: RideFilter): Result<Flow<Ride>>
}

class RideRepositoryImpl(
    private val rideService: RideService,
    private val rideCache: RideCache,
): RideRepository {

    override suspend fun searchDestinations(query: String): Result<RideSearchResult> {
        return rideService.searchDestinations(query)
            .onSuccess {
                rideCache.saveDestinations(query, it)
            }
            .onFailure {
                rideCache.getDestinations(query)
            }
    }

    override suspend fun postRide(ride: Ride): Result<Ride> {
        return rideService.postRide(ride)
            .onSuccess {
                rideCache.saveRide(ride)
            }
    }

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
    val destinations: MutableSet<Location> = mutableSetOf(),
): RideRepository {

    private val maximumLevenshteinDistance = 20

    /**
     * Calculates the Levenshtein distance between this string and another.
     * @return the number of insertions, deletions, and substitutions needed to transform this string into the other.
     */
    private fun String.levenshtein(other: String): Int {
        val s = this
        val t = other
        val d = Array(s.length + 1) { IntArray(t.length + 1) }
        for (i in 0..s.length) {
            d[i][0] = i
        }
        for (j in 0..t.length) {
            d[0][j] = j
        }
        for (i in 1..s.length) {
            for (j in 1..t.length) {
                val cost = if (s[i - 1] == t[j - 1]) 0 else 1
                d[i][j] = minOf(
                    d[i - 1][j] + 1,
                    d[i][j - 1] + 1,
                    d[i - 1][j - 1] + cost
                )
            }
        }
        return d[s.length][t.length]
    }

    override suspend fun searchDestinations(query: String): Result<RideSearchResult> {
        val candidates = destinations.flatMap {
            val score = it.name.levenshtein(query)
            if (score < maximumLevenshteinDistance) {
                listOf(it to score)
            } else {
                emptyList()
            }
        }.sortedBy { it.second }.map { it.first }

        return Result.success(RideSearchResult(candidates))
    }



    override suspend fun postRide(ride: Ride): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun cancelRide(ride: Ride): Result<Ride> {
        TODO("Not yet implemented")
    }

    override suspend fun watchRides(filter: RideFilter): Result<Flow<Ride>> {
        TODO("Not yet implemented")
    }
}
