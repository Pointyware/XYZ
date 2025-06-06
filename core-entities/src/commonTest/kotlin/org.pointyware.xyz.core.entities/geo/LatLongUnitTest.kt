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
class LatLongUnitTest {
    @Test
    fun calculation() {
        /*
        Given:
        - Two locations
         */
        val start = LatLong(36.1145791,-97.1186917)
        val end = LatLong(36.114579,-97.1184657)

        /*
        When:
        - Calculating the distance between them
         */
        val distance = start.distanceTo(end)

        /*
        Then:
        - The distance should be correct
         */
        distance.value shouldBeLessThan  0.05
    }

    infix fun Double.shouldBeCloseTo(expected: Double) {
        val epsilon = 0.01
        assertEquals(expected, this, epsilon)
    }

    infix fun Double.shouldBeLessThan(expected: Double) {
        assertTrue(this < expected, "$this should be less than $expected")
    }
}
