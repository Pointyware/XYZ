/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.model

/**
 *
 */
data class UserCredentials(
    val email: String,
    val hash: String,
)
