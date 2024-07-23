/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import org.pointyware.xyz.core.entities.profile.RiderProfile

/**
 * Represents a request for a ride.
 */
data class Request(
    val time: Time,
    val route: Route,
    val rider: RiderProfile
)
