/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.geo

import kotlin.math.pow

/**
 *
 */
interface Length: Comparable<Length> {
    val value: Double
    val unit: LengthUnit

    fun to(otherUnit: LengthUnit): Length
    fun format(): String
    operator fun plus(other: Length): Length {
        val otherToThis = other.to(unit)
        return LengthValue(value + otherToThis.value, unit)
    }
}

enum class LengthUnit(
    val metersPerUnit: Double,
    val symbol: String
) {
    MILES(1609.34, "mi"),
    KILOMETERS(1000.0, "km"),
    METERS(1.0, "m"),
    FEET(0.3048, "ft"),
}

data class LengthValue(
    override val value: Double,
    override val unit: LengthUnit
): Length {
    override fun compareTo(other: Length): Int {
        val otherToThis = other.to(unit)
        return value.compareTo(otherToThis.value)
    }

    override fun to(otherUnit: LengthUnit): Length {
        val newValue = value * unit.metersPerUnit / otherUnit.metersPerUnit
        return LengthValue(newValue, otherUnit)
    }

    private val expectedDecimalPlaces = 2
    private val shiftFactor = 10.0.pow(expectedDecimalPlaces)
    override fun format(): String {
        val shiftedValue = value * shiftFactor
        val roundedValue = (shiftedValue + if (value > 0) { 0.5 } else {-0.5}).toLong() // add/subtract 0.5 to round to nearest
        val roundedString = roundedValue.toString()
        val whole = roundedString.substring(0, roundedString.length - expectedDecimalPlaces)
        val fraction = roundedString.substring(roundedString.length - expectedDecimalPlaces)

        return "$whole.$fraction ${unit.symbol}"
    }
}

fun Double.miles(): LengthValue = LengthValue(this, LengthUnit.MILES)
fun Double.kilometers(): LengthValue = LengthValue(this, LengthUnit.KILOMETERS)
fun Double.meters(): LengthValue = LengthValue(this, LengthUnit.METERS)
fun Double.feet(): LengthValue = LengthValue(this, LengthUnit.FEET)
