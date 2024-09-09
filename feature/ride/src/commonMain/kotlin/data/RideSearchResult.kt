/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

import org.pointyware.xyz.core.entities.ride.Location

/**
 *
 */
data class RideSearchResult(
//    val id: String,
    val destinations: List<Location>
)
