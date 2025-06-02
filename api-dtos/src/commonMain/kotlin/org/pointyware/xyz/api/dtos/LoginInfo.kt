/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LoginInfo(
    val email: String,
    val password: String,
)
