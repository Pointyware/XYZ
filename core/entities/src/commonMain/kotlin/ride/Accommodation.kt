/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import kotlinx.serialization.Serializable

/**
 * Describes a driver accommodation that can be provided to a rider with a [Disability].
 */
@Serializable
sealed class Accommodation {
    data object WheelchairAccess : Accommodation()
    data object AnimalFriendly : Accommodation()
}
