/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

import org.pointyware.xyz.core.common.Regex
/**
 * TODO: describe purpose/intent of PhoneNumber
 */
data class PhoneNumber(
    @Regex("^[0-9]+$")
    val sequence: String
)
