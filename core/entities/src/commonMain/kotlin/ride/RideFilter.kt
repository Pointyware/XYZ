/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.geo.Length

/**
 * Defines a filter for rides a driver will accept.
 */
interface RideFilter {
    fun accepts(candidate: Ride): Boolean

    val serviceArea: Area?
    val timeRange: ClosedRange<Instant>?
    val distanceRange: ClosedRange<Length>?
    val priceRange: ClosedRange<Currency>?
    val rateRange: ClosedRange<Currency>?
}

private data class SimpleRideFilter(
    override val serviceArea: Area? = null,
    override val timeRange: ClosedRange<Instant>? = null,
    override val distanceRange: ClosedRange<Length>? = null,
    override val priceRange: ClosedRange<Currency>? = null,
    override val rateRange: ClosedRange<Currency>? = null,
): RideFilter {
    override fun accepts(candidate: Ride): Boolean {
//        if (priceRange != null && !priceRange.contains(candidate.)) {
//            return false
//        } TODO: add bid price to ride request
//        if (rateRange != null && !rateRange.contains(candidate.rate)) {
//            return false
//        } TODO: add bid rate to ride request
        val startCoordinates = candidate.plannedRoute?.start?.coordinates
        if (serviceArea != null && startCoordinates != null && !serviceArea.contains(startCoordinates)) {
            return false
        }
        val routeDistance = candidate.plannedRoute?.distance
        if (distanceRange != null && routeDistance != null && !distanceRange.contains(routeDistance)) {
            return false
        }
        val pickupTime = when (val status = candidate.status) { is Ride.Status.Scheduled -> status.timeToStart
            else -> null
        }
        if (timeRange != null && pickupTime != null && !timeRange.contains(pickupTime)) {
            return false
        }
        return true
    }
}
