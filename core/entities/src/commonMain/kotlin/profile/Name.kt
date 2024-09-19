/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.profile

import kotlinx.serialization.Serializable
import org.pointyware.xyz.core.common.StringLength

/**
 *
 */
@Serializable
data class Name(
    @StringLength(1, 32)
    val given: String,
    @StringLength(1, 64)
    val middle: String,
    @StringLength(1, 32)
    val family: String,
)
