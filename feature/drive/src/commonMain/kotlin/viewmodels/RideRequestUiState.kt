/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.business.Rate
import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.entities.geo.Route

/**
 * Displays information about a new ride request: distance from driver, distance of route, and rider service rate.
 */
interface RideRequestUiState {
    val requestId: Uuid
    val riderName: String
    val route: Route
    val distanceFromDriver: Length
    val riderServiceRate: Rate
    val totalFair: Currency
}

data class RideRequestUiStateImpl(
    override val requestId: Uuid,
    override val riderName: String,
    override val route: Route,
    override val distanceFromDriver: Length,
    override val riderServiceRate: Rate,
): RideRequestUiState {
    override val totalFair: Currency
        get() = riderServiceRate * route.distance
}
