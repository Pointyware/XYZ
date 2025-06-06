/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive.entities

import kotlinx.datetime.Instant
import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.business.Rate
import org.pointyware.xyz.core.entities.profile.RiderProfile
import org.pointyware.xyz.core.entities.geo.Route
import kotlin.uuid.ExperimentalUuidApi

/**
 * Represents a request for a new ride.
 */
@OptIn(ExperimentalUuidApi::class)
data class Request(
    val rideId: Uuid,
    val rider: RiderProfile,
    val route: Route,
    val rate: Rate,
    val timePosted: Instant
) {
    val fare: Currency = rate * route.distance
}
