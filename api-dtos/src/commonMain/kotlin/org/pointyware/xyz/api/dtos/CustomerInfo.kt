/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

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
