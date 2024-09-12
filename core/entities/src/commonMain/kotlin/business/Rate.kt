/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.business

import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.entities.geo.LengthUnit

/**
 * Represents a rate of currency per length.
 */
data class Rate(
    val currency: Currency,
    val denominator: LengthUnit
): Comparable<Rate> {
    operator fun times(length: Length): Currency {
        val consistentLength = length.to(denominator)
        val ratedAmount = currency.amount * consistentLength.value
        return Currency(ratedAmount.toLong(), currency.form)
    }

    override fun compareTo(other: Rate): Int {
        val leftCurrency: Currency
        val rightCurrency: Currency

        when {
            denominator.metersPerUnit > other.denominator.metersPerUnit -> {
                val scaling = denominator.metersPerUnit / other.denominator.metersPerUnit
                leftCurrency = currency
                rightCurrency = Currency((other.currency.amount * scaling).toLong(), other.currency.form)
            }
            denominator.metersPerUnit < other.denominator.metersPerUnit -> {
                val scaling = other.denominator.metersPerUnit / denominator.metersPerUnit
                leftCurrency = Currency((currency.amount * scaling).toLong(), currency.form)
                rightCurrency = other.currency
            }
            else -> {
                leftCurrency = currency
                rightCurrency = other.currency
            }
        }
        return leftCurrency compareTo rightCurrency
    }

    fun format(): String {
        return "${currency.format()}/${denominator.symbol}"
    }

    companion object {
        operator fun Currency.div(length: Length): Rate {
            val scaledCurrency = Currency((amount * length.value).toLong(), form)
            return Rate(scaledCurrency, length.unit)
        }

        infix fun Currency.per(length: Length): Rate {
            return div(length)
        }

        operator fun Currency.div(unit: LengthUnit): Rate {
            return Rate(this, unit)
        }
    }
}
