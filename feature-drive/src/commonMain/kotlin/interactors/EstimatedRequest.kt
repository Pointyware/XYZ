/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive.interactors

import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.drive.entities.DriverRates
import org.pointyware.xyz.drive.entities.Request

/**
 * A request with estimated pickup distance and driver rates.
 */
data class EstimatedRequest(
    val request: Request,
    val pickupDistance: Length,
    val driverRates: DriverRates
)
