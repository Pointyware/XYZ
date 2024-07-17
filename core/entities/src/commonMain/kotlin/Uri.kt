/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

/**
 *
 */
class Uri(
    val value: String
) {

    init {
        require(value.isNotBlank()) { "Uri cannot be blank" }
    }
}
