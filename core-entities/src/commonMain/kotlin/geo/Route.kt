/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.geo

import kotlin.time.Duration

/**
 *
 */
data class Route (
    /**
     * The starting point of the route.
     */
    val start: Location,

    /*
     * The intermediate points along the route.
     */
    val intermediates: List<Location>,

    /**
     * The ending point of the route.
     */
    val end: Location,

    /**
     * The total distance of the route.
     */
    val distance: Length,

    /**
     * The total duration of the route, estimated for a planned route or measured for an actual path.
     * @see Ride.plannedRoute
     * @see Ride.Status.RouteProgress.Realized
     */
    val duration: Duration,
)
