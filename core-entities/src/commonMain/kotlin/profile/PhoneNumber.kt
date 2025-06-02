/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.profile

import kotlinx.serialization.Serializable
import org.pointyware.xyz.core.common.Regex

/**
 * A sequence of digits (at least 1) representing a phone number.
 */
@Serializable
data class PhoneNumber(
    @Regex("^[0-9]+$")
    val sequence: String
)
