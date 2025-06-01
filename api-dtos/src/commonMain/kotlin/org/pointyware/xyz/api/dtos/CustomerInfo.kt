package org.pointyware.xyz.api.dtos

import kotlinx.serialization.Serializable

/**
 * Used by the client to send customer info to the server.
 * Used by the server to receiver customer info from the client.
 */
@Serializable
data class CustomerInfo(
    val id: String,
)
