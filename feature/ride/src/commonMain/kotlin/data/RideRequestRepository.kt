/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.ride.Location
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.entities.ride.Route

/**
 * Handles requests for rides.
 */
interface RideRequestRepository {
    suspend fun searchDestinations(query: String): Result<RideSearchResult>
    suspend fun requestRide(route: Route): Result<Ride>
    suspend fun scheduleRide(route: Route, time: Instant): Result<Ride>
}

/**
 *
 */
class RideRequestRepositoryImpl(
    private val cache: RideRequestCache,
    private val service: RideRequestService,
): RideRequestRepository {

    override suspend fun searchDestinations(query: String): Result<RideSearchResult> {
        return service.searchDestinations(query)
            .onSuccess {
                cache.saveDestinations(query, it)
            }
            .onFailure {
                cache.getDestinations(query)
            }
    }

    override suspend fun requestRide(route: Route): Result<Ride> {
        TODO("")
    }

    override suspend fun scheduleRide(route: Route, time: Instant): Result<Ride> {
        TODO("Not yet implemented")
    }
}

/**
 *
 */
class TestRideRequestRepository(
    val destinations: MutableSet<Location> = mutableSetOf(),
    val dataScope: CoroutineScope,
): RideRequestRepository {

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

    override suspend fun requestRide(route: Route): Result<Ride> {
        TODO("")
    }

    override suspend fun scheduleRide(route: Route, time: Instant): Result<Ride> {
        TODO("Not yet implemented")
    }
}
