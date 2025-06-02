/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

import kotlinx.serialization.Serializable
import org.pointyware.xyz.core.common.StringLength

/**
 * A person's name.
 */
@Serializable
data class Name(
    /**
     * A portion given to a person, usually by their parents, serving as their primary identifier.
     */
    @StringLength(1, 32)
    val given: String,

    /**
     * A portion given to a person, usually by their parents, serving as their secondary identifier.
     */
    @StringLength(1, 64)
    val middle: String,

    /**
     * A portion inherited from a person's family.
     */
    @StringLength(1, 32)
    val family: String,
)
