/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive

import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.business.Rate
import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.entities.geo.Area
import org.pointyware.xyz.drive.entities.Request

/**
 * Defines a filter for rides a driver will accept.
 */
interface RideFilter {
    fun accepts(candidate: Request): Boolean

    val serviceArea: Area?
//    val timeRange: ClosedRange<Instant>? TODO: implement scheduled rides
    val distanceRange: ClosedRange<Length>?
    val priceRange: ClosedRange<Currency>?
    val rateRange: ClosedRange<Rate>?
}

data class SimpleRideFilter(
    override val serviceArea: Area? = null,
//    override val timeRange: ClosedRange<Instant>? = null,
    override val distanceRange: ClosedRange<Length>? = null,
    override val priceRange: ClosedRange<Currency>? = null,
    override val rateRange: ClosedRange<Rate>? = null,
): RideFilter {
    override fun accepts(candidate: Request): Boolean {
        if (priceRange != null && !priceRange.contains(candidate.fare)) {
            return false
        }
        if (rateRange != null && !rateRange.contains(candidate.rate)) {
            return false
        }
        val startCoordinates = candidate.route.start.coordinates
        if (serviceArea != null && !serviceArea.contains(startCoordinates)) {
            return false
        }
        val routeDistance = candidate.route.distance
        if (distanceRange != null && !distanceRange.contains(routeDistance)) {
            return false
        }
        return true
    }

    companion object {
        val Permissive = SimpleRideFilter()
    }
}
