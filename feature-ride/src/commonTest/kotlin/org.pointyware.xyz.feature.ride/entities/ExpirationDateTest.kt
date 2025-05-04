/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.entities

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

/**
 *
 */
class ExpirationDateTest {

    data class FormatCase(
        val month: Byte,
        val year: Short,
        val expected: String
    )

    @Test
    fun `format expiration date`() {
        /*
        Given:
        - A month and year
        When:
        - Formatting the expiration date
         */
        listOf(
            FormatCase(1, 2024, "01/24"),
            FormatCase(12, 2024, "12/24"),
            FormatCase(2, 2025, "02/25"),
            FormatCase(10, 2025, "10/25"),
        ).forEach { (month, year, expected) ->
            val expirationDate = ExpirationDate(month, year)
            val formatted = expirationDate.format()
            /*
            Then:
            - The expiration date should be formatted as MM/YY
             */
            assertEquals(expected, formatted, "Expected $expected but got $formatted for $expirationDate")
        }
    }

    data class ConstructionCase(
        val month: Byte,
        val year: Short,
    )

    @Test
    fun `invalid expiration month`() {
        /*
        Given:
        - An invalid month
        When:
        - Creating an expiration date
        Then:
        - An exception should be thrown
         */
        listOf(
            ConstructionCase(-128, 2024),
            ConstructionCase(0, 2024),
            ConstructionCase(13, 2024),
            ConstructionCase(127, 2024),
        ).forEach { (month, year) ->
            assertFails("Expected failure for month number $month") {
                ExpirationDate(month, year)
            }
        }
    }

    @Test
    fun `invalid expiration year`() {
        /*
        Given:
        - An invalid year
        When:
        - Creating an expiration date
        Then:
        - An exception should be thrown
         */
        listOf(
            ConstructionCase(4, 2020),
            ConstructionCase(3, 2021),
            ConstructionCase(2, 2022),
            ConstructionCase(1, 2023),
            ConstructionCase(5, 3024),
            ConstructionCase(6, 3025),
            ConstructionCase(7, 3026),
            ConstructionCase(8, 3027),
        ).forEach { (month, year) ->
            assertFails("Expected failure for year $year") {
                ExpirationDate(month, year)
            }
        }
    }
}
