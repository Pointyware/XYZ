/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.entities.geo.Location

/**
 * Displays information about a new ride request: distance from driver, distance of route, and rider service rate.
 */
interface RideRequestUiState {
    val destination: Location
    val distanceFromDriver: Length
    val distanceOfRoute: Length
//    val riderServiceRate: Currency // TODO: create rate entity
    val totalFair: Currency
}
