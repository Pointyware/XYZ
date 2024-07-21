/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import org.pointyware.xyz.core.entities.geo.LatLong
import org.pointyware.xyz.core.entities.geo.Length

/**
 * Describes a geographical area.
 */
sealed interface Area {
    data class Circular(val center: LatLong, val radius: Length): Area
    data class Rectangular(val topLeft: LatLong, val bottomRight: LatLong): Area
    data class Polygonal(val vertices: List<LatLong>): Area
}
