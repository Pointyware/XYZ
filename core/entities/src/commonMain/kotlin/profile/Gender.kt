/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.profile

import kotlinx.serialization.Serializable

/**
 * Known instances of self representation.
 */
@Serializable
enum class Gender {
    Man,
    Woman,
    Nonbinary,
    GenderFluid,
    TransMasc,
    TransFem,
    Nonconforming,
    NotSpecified
}
