/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import org.pointyware.xyz.core.entities.profile.Profile

/**
 * A review of a rider or driver.
 */
data class Review(
    val poster: Profile,
    val rating: Rating,
    val comment: String
)
