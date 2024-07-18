/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

import kotlin.math.pow

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

    fun format(): String {
        return form.format(amount)
    }

    sealed class Form(
        val prefix: String,
        val postfix: String,
        val name: String,
        val code: String,
    ) {

        abstract fun format(amount: Long): String

        class SimpleForm(
            prefix: String,
            postfix: String,
            name: String,
            code: String,
        ): Form(prefix, postfix, name, code) {
            override fun format(amount: Long): String {
                return "$prefix$amount$postfix"
            }
        }

        class DecimalForm(
            prefix: String,
            postfix: String,
            name: String,
            code: String,
            val decimalPlaces: Int,
        ): Form(prefix, postfix, name, code) {
            override fun format(amount: Long): String {
                val fractions = 10L pow decimalPlaces
                val whole = amount / fractions
                val remainder = amount % fractions
                return "$prefix$whole.$remainder$postfix"
            }
        }


        companion object {
            val usDollars = SimpleForm(
                prefix = "$",
                postfix = "",
                name = "US Dollars",
                code = "USD",
            )
            val usDollarCents = DecimalForm(
                prefix = "$",
                postfix = "",
                name = "US Dollars and Cents",
                code = "USD.CENTS",
                decimalPlaces = 2,
            )
        }
    }
}
internal class SimpleCurrency(
    override val amount: Long,
    override val form: Currency.Form,
): Currency {
    override fun compareTo(other: Currency): Int {
        if (form != other.form) {
            throw IllegalArgumentException("Cannot compare different currency forms") // TODO: support conversion
        }
        return amount.compareTo(other.amount)
    }
}
fun Currency(amount: Long, form: Currency.Form): Currency {
    return SimpleCurrency(amount, form)
}
