/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.business.Rate
import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.entities.geo.Route
import org.pointyware.xyz.drive.entities.DriverRates
import kotlin.uuid.ExperimentalUuidApi

/**
 * Displays information about a new trip request: distance from driver, distance of route, and rider service rate.
 */
@OptIn(ExperimentalUuidApi::class)
interface RideRequestUiState {
    val requestId: Uuid
    val riderName: String
    val route: Route
    val riderServiceRate: Rate
    val totalFair: Currency

    val pickupDistance: Length
    val dropoffDistance: Length
    val totalDistance: Length

    val pickupRate: Rate
    val dropoffRate: Rate
    val maintenanceRate: Rate

    val pickupPrice: Currency
    val dropoffPrice: Currency

    val pickupCost: Currency
    val dropoffCost: Currency

    val grossProfit: Currency
}

@OptIn(ExperimentalUuidApi::class)
data class RideRequestUiStateImpl(
    override val requestId: Uuid,
    override val riderName: String,
    override val route: Route,
    override val riderServiceRate: Rate,

    override val pickupDistance: Length,
    val driverRates: DriverRates
): RideRequestUiState {
    override val totalFair: Currency
        get() = riderServiceRate * route.distance

    override val dropoffDistance: Length
        get() = route.distance
    override val totalDistance: Length
        get() = pickupDistance + dropoffDistance

    override val pickupRate: Rate
        get() = driverRates.pickupCost
    override val dropoffRate: Rate
        get() = driverRates.dropoffCost
    override val maintenanceRate: Rate
        get() = driverRates.maintenanceCost

    override val pickupPrice: Currency
        get() = pickupRate * pickupDistance
    override val dropoffPrice: Currency
        get() = dropoffRate * dropoffDistance
    override val grossProfit: Currency
        get() = pickupPrice + dropoffPrice

    override val pickupCost: Currency
        get() = maintenanceRate * pickupDistance
    override val dropoffCost: Currency
        get() = maintenanceRate * dropoffDistance
}
