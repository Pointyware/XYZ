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
