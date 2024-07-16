/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.drive.viewmodels

import org.pointyware.xyz.core.entities.Currency

/**
 * Displays information about a new ride request: distance from driver, distance of route, and rider service rate.
 */
interface RideRequestUiState {
    val distanceFromDriver: Double
    val distanceOfRoute: Double
    val riderServiceRate: Currency
}
