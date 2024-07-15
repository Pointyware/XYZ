/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.entities

/**
 * A review of a rider or driver.
 */
data class Review(
    val poster: Profile,
    val rating: Rating,
    val comment: String
)
