/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.geo

/**
 * Describes a geographical area.
 */
sealed interface Area {
    fun contains(coordinates: LatLong): Boolean

    data class Circular(val center: LatLong, val radius: Length): Area {
        override fun contains(coordinates: LatLong): Boolean {
            return center.distanceTo(coordinates) <= radius.to(LengthUnit.KILOMETERS)
        }
    }

    data class Rectangular(val topLeft: LatLong, val bottomRight: LatLong): Area {
        override fun contains(coordinates: LatLong): Boolean {
            return coordinates.latitude in bottomRight.latitude..topLeft.latitude
                    && coordinates.longitude in topLeft.longitude..bottomRight.longitude
        }
    }

    data class Polygonal(val vertices: List<LatLong>): Area {
        override fun contains(coordinates: LatLong): Boolean {
            var inside = false
            var j = vertices.size - 1
            for (i in vertices.indices) {
                if (vertices[i].longitude < coordinates.longitude && vertices[j].longitude >= coordinates.longitude
                    || vertices[j].longitude < coordinates.longitude && vertices[i].longitude >= coordinates.longitude) {
                    if (vertices[i].latitude + (coordinates.longitude - vertices[i].longitude) / (vertices[j].longitude - vertices[i].longitude) * (vertices[j].latitude - vertices[i].latitude) < coordinates.latitude) {
                        inside = !inside
                    }
                }
                j = i
            }
            return inside
        }
    }
}
