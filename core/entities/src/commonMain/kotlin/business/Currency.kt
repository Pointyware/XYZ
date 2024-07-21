/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
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

    fun format(): String {
        return form.format(amount)
    }

    sealed class Form(
        val prefix: String,
        val postfix: String,
        val name: String,
        val code: String,
        val centsPerUnit: Long,
    ) {

        abstract fun format(amount: Long): String

        class SimpleForm(
            prefix: String,
            postfix: String,
            name: String,
            code: String,
            centsPerUnit: Long
        ): Form(prefix, postfix, name, code, centsPerUnit) {
            override fun format(amount: Long): String {
                return "$prefix$amount$postfix"
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
        }
    }
}
internal class SimpleCurrency(
    override val amount: Long,
    override val form: Currency.Form,
): Currency {
    override fun compareTo(other: Currency): Int {
        val otherValue = other.amount * form.centsPerUnit / other.form.centsPerUnit
        return amount.compareTo(otherValue)
    }
}
fun Currency(amount: Long, form: Currency.Form): Currency {
    return SimpleCurrency(amount, form)
}
