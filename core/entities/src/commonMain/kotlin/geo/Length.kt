/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.geo

/**
 *
 */
interface Length: Comparable<Length> {
    val value: Double
    val unit: LengthUnit

    fun to(otherUnit: LengthUnit): Length
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

    override fun toString(): String {
        return "$value ${unit.symbol}"
    }
}

fun Double.miles(): LengthValue = LengthValue(this, LengthUnit.MILES)
fun Double.kilometers(): LengthValue = LengthValue(this, LengthUnit.KILOMETERS)
fun Double.meters(): LengthValue = LengthValue(this, LengthUnit.METERS)
fun Double.feet(): LengthValue = LengthValue(this, LengthUnit.FEET)
