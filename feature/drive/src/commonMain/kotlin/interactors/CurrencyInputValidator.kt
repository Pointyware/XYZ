/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.interactors

import org.pointyware.xyz.core.entities.business.Currency

/**
 *
 */
class CurrencyInputValidator {
    fun validate(currency: String): Boolean {
        return currency.matches(Regex("^[0-9]+(\\.[0-9]{1,2})?$"))
    }

    fun parse(input: String): Currency {
        val parts = input.split(".")
        val dollars = parts[0].toLong()
        val cents = parts.getOrNull(1)?.toLong() ?: 0
        return Currency(dollars*100 + cents, Currency.Form.usDollarCents)
    }
}