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
    val serviceArea: Area
    val timeRange: ClosedRange<Instant>
    val distanceRange: ClosedRange<Length>
    val priceRange: ClosedRange<Currency>
    val rateRange: ClosedRange<Currency>
}
