/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.business

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 */
class CurrencyUnitTest {

    data class FormattingCase(
        val amount: Long,
        val form: Currency.Form,
        val expected: String
    )

    @Test
    fun testSimpleForm() {
        listOf(
            FormattingCase(
                100,
                Currency.Form.usDollars,
                "$100"
            ),
            FormattingCase(
                56,
                Currency.Form.usDollars,
                "$56"
            ),
        ).forEach { (amount, form, expected) ->
            val currency = Currency(amount, form)

            val string = currency.format()

            assertEquals(expected, string)
        }
    }

    data class ComparisonCase(
        val amount1: Long,
        val form1: Currency.Form,
        val amount2: Long,
        val form2: Currency.Form,
        val expected: Int
    )

    @Test
    fun `comparison should `() {
        listOf(
            ComparisonCase(
                1,
                Currency.Form.usDollars,
                100,
                Currency.Form.usDollarCents,
                0
            ),
            ComparisonCase(
                56,
                Currency.Form.usDollars,
                5610,
                Currency.Form.usDollarCents,
                -1
            ),
            ComparisonCase(
                56,
                Currency.Form.usDollarCents,
                54,
                Currency.Form.usDollarCents,
                1
            ),
        ).forEach { (amount1, form1, amount2, form2, expected) ->

            val left = Currency(amount1, form1)
            val right = Currency(amount2, form2)
            val comparison = left.compareTo(right)

            assertEquals(expected, comparison)
        }
    }

    @Test
    fun `dollars should return a currency with the correct form`() {
        val amount = 100L
        val currency = amount.dollars()

        assertEquals(amount, currency.amount)
        assertEquals(Currency.Form.usDollars, currency.form)
    }

    @Test
    fun `cents should return a currency with the correct form`() {
        val amount = 100L
        val currency = amount.dollarCents()

        assertEquals(amount, currency.amount)
        assertEquals(Currency.Form.usDollarCents, currency.form)
    }
}
