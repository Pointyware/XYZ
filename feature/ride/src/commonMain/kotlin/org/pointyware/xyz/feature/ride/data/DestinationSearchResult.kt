/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

import org.pointyware.xyz.core.entities.geo.Location

/**
 *
 */
data class DestinationSearchResult(
//    val id: String,
    val destinations: List<Location>
)
