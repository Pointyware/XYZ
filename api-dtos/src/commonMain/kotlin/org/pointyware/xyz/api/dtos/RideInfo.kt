/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.dtos

import kotlinx.serialization.Serializable

/**
 * Information about a past completed ride.
 */
@Serializable
data class RideInfo(
    val riderId: String,
    val cost: Long,
)
