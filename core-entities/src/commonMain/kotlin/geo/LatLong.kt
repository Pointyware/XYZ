/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.geo

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin


const val EARTH_RADIUS = 6371.0

/**
 * A simple data class to represent a geographic location in latitude and longitude.
 */
data class LatLong(
    val latitude: Double,
    val longitude: Double
) {
    /**
     * Calculate the distance between this location and another in kilometers assuming a
     * spherical Earth of radius [EARTH_RADIUS] km.
     */
    fun distanceTo(coordinates: LatLong): Length {
        val lat1 = latitude.toRadians()
        val lon1 = longitude.toRadians()
        val lat2 = coordinates.latitude.toRadians()
        val lon2 = coordinates.longitude.toRadians()

        val lonDif = lon2 - lon1

        val centralAngle = acos(sin(lat1)*sin(lat2) + cos(lat1)*cos(lat2)*cos(lonDif))

        return abs(EARTH_RADIUS * centralAngle).kilometers()
    }
}
