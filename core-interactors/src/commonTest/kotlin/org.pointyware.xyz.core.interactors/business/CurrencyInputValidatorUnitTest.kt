/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.interactors.business

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 *
 */
class CurrencyInputValidatorUnitTest {

    private lateinit var validator: CurrencyInputValidator

    @BeforeTest
    fun setUp() {
        validator = CurrencyInputValidator()
    }

    @Test
    fun `empty string is valid`() {
        val input = ""

        val result = validator.validate(input)

        assertTrue(result, "Empty string should be valid")
    }

    @Test
    fun `partial input is valid`() {
        val input = "1."

        val result = validator.validate(input)

        assertTrue(result, "Partial input should be valid")
    }

    @Test
    fun `letter input is valid`() {
        val input = "1.a"

        val result = validator.validate(input)

        assertFalse(result, "Letters in input should not be valid")
    }

    @Test
    fun `1 decimal is valid`() {
        val input = "1.0"

        val result = validator.validate(input)

        assertTrue(result, "1 decimal should be valid")
    }

    @Test
    fun `2 decimals is valid`() {
        val input = "1.23"

        val result = validator.validate(input)

        assertTrue(result, "2 decimals should be valid")
    }

    @Test
    fun `3 decimals is invalid`() {
        val input = "1.234"

        val result = validator.validate(input)

        assertFalse(result, "3 decimals should not be valid")
    }

    @Test
    fun `long number is valid`() {
        val input = "1234567890.12"

        val result = validator.validate(input)

        assertTrue(result, "Long number should be valid")
    }
}
