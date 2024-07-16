/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.entities

/**
 * Describes a driver accommodation that can be provided to a rider with a [Disability].
 */
sealed class Accommodation {
    data object WheelchairAccess : Accommodation()
    data object AnimalFriendly : Accommodation()
}
