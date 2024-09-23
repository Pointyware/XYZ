/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.entities.ride.Ride
import org.pointyware.xyz.core.entities.geo.Route
import kotlin.time.Duration.Companion.milliseconds

/**
 * Handles trip search and scheduling.
 */
interface TripRepository {
    /**
     * The current trip being taken by the user.
     */
    val currentTrip: StateFlow<Ride?>

    suspend fun searchDestinations(query: String): Result<DestinationSearchResult>
    suspend fun findRoute(origin: Location, destination: Location): Result<Route>
    suspend fun requestRide(route: Route): Result<Ride>
    suspend fun scheduleRide(route: Route, time: Instant): Result<Ride>
}

/**
 *
 */
class TripRepositoryImpl(
    private val cache: TripCache,
    private val service: TripService,
): TripRepository {

    override val currentTrip: StateFlow<Ride?>
        get() = TODO("Not yet implemented")

    override suspend fun searchDestinations(query: String): Result<DestinationSearchResult> {
        return service.searchDestinations(query)
            .onSuccess {
                cache.saveDestinations(query, it)
            }
            .onFailure {
                cache.getDestinations(query)
            }
    }

    override suspend fun findRoute(origin: Location, destination: Location): Result<Route> {
        TODO("Not yet implemented")
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
class TestTripRepository(
    val destinations: MutableSet<Location> = mutableSetOf(),
    val dataScope: CoroutineScope,
): TripRepository {

    override val currentTrip: StateFlow<Ride?>
        get() = TODO("Not yet implemented")

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

    override suspend fun searchDestinations(query: String): Result<DestinationSearchResult> {
        val candidates = destinations.flatMap {
            val score = it.name.levenshtein(query)
            if (score < maximumLevenshteinDistance) {
                listOf(it to score)
            } else {
                emptyList()
            }
        }.sortedBy { it.second }.map { it.first }

        return Result.success(DestinationSearchResult(candidates))
    }

    override suspend fun findRoute(origin: Location, destination: Location): Result<Route> {
        val intermediateCount = 5
        val length = origin.coordinates.distanceTo(destination.coordinates)
        val speed = 45.0 / (1000 * 60 * 60) // 45 km/hr
        // TODO: include rate; 120 cents per km
        val route = Route(
            start = origin,
            intermediates = List(5) {
                Location(
                    lat = origin.coordinates.latitude + (destination.coordinates.latitude - origin.coordinates.latitude) * (it + 1) / (intermediateCount + 1),
                    long = origin.coordinates.longitude + (destination.coordinates.longitude - origin.coordinates.longitude) * (it + 1) / (intermediateCount + 1),
                    name = "${origin.name} to ${destination.name} (${it + 1} of $intermediateCount)",
                )
            },
            end = destination,
            distance = length,
            duration = (length.value / speed).toInt().milliseconds
        )
        delay(1500)
        return Result.success(route)
    }

    override suspend fun requestRide(route: Route): Result<Ride> {
        TODO("Not yet implemented")
//        mutableNewRides.emit(ride)
//        mutablePostedRides.update { it + ride }
//        // no limiting criteria in tests
//        return Result.success(ride)
    }

    override suspend fun scheduleRide(route: Route, time: Instant): Result<Ride> {
        TODO("Not yet implemented")
    }
}
