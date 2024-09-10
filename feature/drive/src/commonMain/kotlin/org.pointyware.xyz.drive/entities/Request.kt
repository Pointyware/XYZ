/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.entities

import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.business.Rate
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.core.entities.ride.Route

/**
 * Represents a request for a new ride.
 */
data class Request(
    val rideId: Uuid,
    val rider: RiderProfile,
    val route: Route,
    val rate: Rate
) {
    val fare: Currency = rate * route.distance
}
