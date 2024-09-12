/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.business

import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.entities.geo.LengthUnit
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 */
class RateUnitTest {

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
        val length1: LengthUnit,
        val amount2: Long,
        val form2: Currency.Form,
        val length2: LengthUnit,
        val expected: Int
    )

    @Test
    fun `comparison should `() {
        listOf(

        ).forEach {

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
