/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.entities

import org.pointyware.xyz.core.common.IntRange

private const val MONTH_MIN = 1
private const val MONTH_MAX = 12
private const val YEAR_MIN = 2024
private const val YEAR_MAX = 3023

/**
 * A payment expiration date, composed of a month and year.
 */
data class ExpirationDate(
    /**
     * The 1-indexed month of the expiration date.
     */
    @IntRange(min = MONTH_MIN, max = MONTH_MAX)
    val month: Byte,

    /**
     * The year of the expiration date.
     */
    @IntRange(min = YEAR_MIN, max = YEAR_MAX)
    val year: Short
) {
    init {
        require(month in MONTH_MIN..MONTH_MAX) { "Month must be between 1 and 12" }
        require(year in YEAR_MIN..YEAR_MAX) { "Year must be between 2024 and 3023" }
    }

    /**
     * Formats the expiration date as a string in the format MM/YY.
     */
    fun format(): String {
        val monthStr = month.toString().padStart(2, '0')
        val lastTwo = year % 100
        val yearStr = lastTwo.toString().padStart(2, '0')
        return "$monthStr/$yearStr"
    }
}
