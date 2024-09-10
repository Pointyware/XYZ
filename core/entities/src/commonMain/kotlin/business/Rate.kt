/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.business

import org.pointyware.xyz.core.entities.geo.Length

/**
 * Represents a rate of currency per length.
 */
data class Rate(
    val currency: Currency,
    val denominator: Length
): Comparable<Rate> {
    operator fun times(length: Length): Currency {
        val consistentLength = length.to(denominator.unit)
        val ratedAmount = currency.amount * consistentLength.value / denominator.value
        return Currency(ratedAmount.toLong(), currency.form)
    }

    override fun compareTo(other: Rate): Int {
        TODO("Convert Currency and length to same units and compare")
    }

    companion object {
        operator fun Currency.div(length: Length): Rate {
            return Rate(this, length)
        }

        infix fun Currency.per(length: Length): Rate {
            return Rate(this, length)
        }
    }
}