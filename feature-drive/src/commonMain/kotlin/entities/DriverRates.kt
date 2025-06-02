/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive.entities

import org.pointyware.xyz.core.entities.business.Rate

/**
 * Rates for a driver's services.
 */
data class DriverRates(
    val maintenanceCost: Rate,
    val pickupCost: Rate,
    val dropoffCost: Rate,
)
