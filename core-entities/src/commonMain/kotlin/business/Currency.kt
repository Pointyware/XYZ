/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.business

internal infix fun Long.pow(exponent: Int): Long {
    var result = 1L
    repeat(exponent) {
        result *= this
    }
    return result
}

/**
 *
 */
interface Currency: Comparable<Currency> {
    val amount: Long
    val form: Form

    fun format(includeSymbols: Boolean = true): String {
        return form.format(amount, includeSymbols)
    }
    operator fun plus(other: Currency): Currency {
        when (other.form.centsPerUnit) {
            form.centsPerUnit -> return Currency(amount + other.amount, form)
            else -> {
                val thisValue = amount
                val otherValue = other.amount * other.form.centsPerUnit / form.centsPerUnit
                return Currency(thisValue + otherValue, form)
            }
        }
    }

    sealed class Form(
        val prefix: String,
        val postfix: String,
        val name: String,
        val code: String,
        val centsPerUnit: Long,
    ) {

        abstract fun format(amount: Long, includeSymbols: Boolean = true): String

        class SimpleForm(
            prefix: String,
            postfix: String,
            name: String,
            code: String,
            centsPerUnit: Long
        ): Form(prefix, postfix, name, code, centsPerUnit) {
            override fun format(amount: Long, includeSymbols: Boolean): String {
                return if (includeSymbols) {
                    "$prefix$amount$postfix"
                } else {
                    amount.toString()
                }
            }
        }

        class DecimalForm(
            prefix: String,
            postfix: String,
            name: String,
            code: String,
            centsPerUnit: Long,
            val decimalPlaces: Int,
        ): Form(prefix, postfix, name, code, centsPerUnit) {
            override fun format(amount: Long, includeSymbols: Boolean): String {
                val fractions = 10L pow decimalPlaces
                val whole = amount / fractions
                val remainder = amount % fractions
                val remainderString = if (remainder == 0L) {
                    "0".repeat(decimalPlaces)
                } else {
                    remainder.toString()
                }
                return if (includeSymbols) {
                    "$prefix$whole.$remainderString$postfix"
                } else {
                    "$whole.$remainderString"
                }
            }
        }


        companion object {
            val usDollars = SimpleForm(
                prefix = "$",
                postfix = "",
                name = "US Dollars",
                code = "USD",
                centsPerUnit = 100,
            )
            val usDollarCents = DecimalForm(
                prefix = "$",
                postfix = "",
                name = "US Dollars and Cents",
                code = "USD.CENTS",
                decimalPlaces = 2,
                centsPerUnit = 1,
            )

            /*
            TODO: Add currencies as different areas are supported by the application
            1. currency conversion service will be needed; can't use comparable directly
            2. currency exchange rates will be needed
             */
        }
    }
}
internal data class SimpleCurrency(
    override val amount: Long,
    override val form: Currency.Form,
): Currency {
    override fun compareTo(other: Currency): Int {
        when {
            form.centsPerUnit > other.form.centsPerUnit -> {
                val thisValue = amount * form.centsPerUnit / other.form.centsPerUnit
                return thisValue.compareTo(other.amount)
            }
            form.centsPerUnit < other.form.centsPerUnit -> {
                val otherValue = other.amount * other.form.centsPerUnit / form.centsPerUnit
                return amount.compareTo(otherValue)
            }
            else -> return amount.compareTo(other.amount)
        }
    }

    override fun toString(): String {
        return form.format(amount)
    }
}
fun Currency(amount: Long, form: Currency.Form): Currency {
    return SimpleCurrency(amount, form)
}

fun Long.dollars() = Currency(this, Currency.Form.usDollars)
fun Long.dollarCents() = Currency(this, Currency.Form.usDollarCents)
