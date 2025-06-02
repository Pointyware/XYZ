/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.geo

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 *
 */
class LengthUnitTest {

    @Test
    fun `formatted length string should display two decimals`() {
        listOf(
            2.0.miles() to "2.00 mi",
            2.0.kilometers() to "2.00 km",
            2.0.meters() to "2.00 m",
            2.0.feet() to "2.00 ft",
            2.1.miles() to "2.10 mi",

            1.333333333333333.miles() to "1.33 mi",
            3.1415926535897.kilometers() to "3.14 km",
            1.66666666666.meters() to "1.67 m",

            (-10.0).miles() to "-10.00 mi",
        ).forEach { (length, expected) ->
            val string = length.format()

            assertEquals(expected, string)
        }
    }

    data class ConversionCase(
        val baseMeasure: Length,
        val convertedMeasures: List<Length>,
    )

    @Test
    fun `conversions should be accurate to 5 significant figures`() {
        /*
        Given
        - a list-matrix of 1km in various units
        When
        - converting to other units
        Then
        - the converted values should be accurate to at least 5 decimals
         */
        listOf(
            ConversionCase(
                1.0.kilometers(),
                listOf(
                    0.62137.miles(),
                    1000.0.meters(),
                    3280.84.feet(),
                )
            ),
            ConversionCase(
                0.62137.miles(),
                listOf(
                    1.0.kilometers(),
                    1000.0.meters(),
                    3280.84.feet(),
                )
            ),
            ConversionCase(
                1000.0.meters(),
                listOf(
                    0.62137.miles(),
                    1.0.kilometers(),
                    3280.84.feet(),
                )
            ),
            ConversionCase(
                3280.84.feet(),
                listOf(
                    0.62137.miles(),
                    1.0.kilometers(),
                    1000.0.meters(),
                )
            ),
        ).forEach { case ->
            val base = case.baseMeasure
            case.convertedMeasures.forEach { to ->
                val converted = base.to(to.unit)

                val diff = converted.value - to.value
                val error = diff / to.value
                assertTrue(error < 0.00001)
            }
        }
    }
}
