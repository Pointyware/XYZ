/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import kotlinx.serialization.Serializable

/**
 * Describes a driver accommodation that can be provided to a rider with a [Disability].
 */
@Serializable
sealed class Accommodation(val name: String) { // TODO: replace with resource ID for i18n
    data object WheelchairAccess : Accommodation("Wheelchair Access")
    data object AnimalFriendly : Accommodation("Animal Friendly")
}
