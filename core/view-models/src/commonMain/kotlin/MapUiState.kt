/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.LatLong

/**
 * Represents the state of a map in a implementation-independent way.
 */
data class MapUiState(
    val location: LatLong,
    val style: MapStyle = MapStyle.Street,
    val pointsOfInterest: List<PointOfInterest> = emptyList(),
)

/**
 * Represents the style of a map.
 */
enum class MapStyle {
    /**
     * A visually simple map that emphasizes streets.
     */
    Street,

    /**
     * A visually complex map that includes terrain. Also called "satellite view" by some maps.
     */
    Terrain,

    /**
     * A visually complex map that includes terrain and topographic features.
     */
    Topographic,
}

/**
 * Represents a point of interest on the map.
 */
sealed interface PointOfInterest {
    val location: LatLong

    /**
     * Usually represented as a rider-color X on the map.
     */
    data class Rider(override val location: LatLong): PointOfInterest
    /**
     * Usually represented as a Y on the map.
     */
    data class Origin(override val location: LatLong): PointOfInterest
    /**
     * Usually represented as a Z on the map.
     */
    data class Destination(override val location: LatLong): PointOfInterest
    /**
     * Usually represented as a driver-color dot on the map.
     */
    data class Driver(override val location: LatLong): PointOfInterest
}
