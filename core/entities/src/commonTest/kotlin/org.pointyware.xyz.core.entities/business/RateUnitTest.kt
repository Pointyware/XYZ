/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.business

import org.pointyware.xyz.core.entities.business.Rate.Companion.div
import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.entities.geo.LengthUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

/**
 *
 */
class RateUnitTest {

    data class FormattingCase(
        val amount: Long,
        val form: Currency.Form,
        val lengthUnit: LengthUnit,
        val expected: String
    )

    @Test
    fun `formatting rate returns a string of formatted currency per length-unit`() {
        listOf(
            FormattingCase(
                100,
                Currency.Form.usDollars,
                LengthUnit.METERS,
                "$100/m"
            ),
            FormattingCase(
                56,
                Currency.Form.usDollars,
                LengthUnit.KILOMETERS,
                "$56/km"
            ),
            FormattingCase(
                100,
                Currency.Form.usDollarCents,
                LengthUnit.FEET,
                "$1.00/ft"
            ),
            FormattingCase(
                56,
                Currency.Form.usDollarCents,
                LengthUnit.MILES,
                "$0.56/mi"
            ),
        ).forEach { (amount, form, unit, expected) ->
            val rate = Rate(Currency(amount, form), unit)

            val string = rate.format()

            assertEquals(expected, string)
        }
    }

    data class RatingCase(
        val amount: Long,
        val form: Currency.Form,
        val length: LengthUnit
    )

    @Test
    fun `dividing currency by length should return a rate`() {
        listOf(
            RatingCase(
                100,
                Currency.Form.usDollars,
                LengthUnit.METERS
            ),
            RatingCase(
                56,
                Currency.Form.usDollars,
                LengthUnit.KILOMETERS,
            ),
            RatingCase(
                100,
                Currency.Form.usDollarCents,
                LengthUnit.FEET,
            ),
            RatingCase(
                56,
                Currency.Form.usDollarCents,
                LengthUnit.MILES,
            ),
        ).forEach { (amount, form, unit) ->
            val currency = Currency(amount, form)
            val expected = Rate(Currency(amount, form), unit)

            val rate = currency / unit

            assertEquals(expected, rate)
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
    fun `comparison should compare converted values across units`() {
//        listOf(
//
//        ).forEach {
//
//            val left = Currency(amount1, form1)
//            val right = Currency(amount2, form2)
//            val comparison = left.compareTo(right)
//
//            assertEquals(expected, comparison)
//        }
        fail("Not yet implemented")
    }
}
