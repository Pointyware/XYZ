/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.entities

import org.pointyware.xyz.core.common.IntRange

/**
 * A payment expiration date, composed of a month and year.
 */
data class ExpirationDate(
    /**
     *
     */
    @IntRange(min = 1, max = 12)
    val month: Byte,
    /**
     *
     */
    @IntRange(min = 2024)
    val year: Short
) {
    init {
        require(month in 1..12) { "Month must be between 1 and 12" }
        require(year >= 2024) { "Year must be 2024 or later" }
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
