/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

/**
 *
 */
interface Length: Comparable<Length> {
    // TODO: add conversion methods
}

internal data class Miles(val value: Double): Length {
    override fun compareTo(other: Length): Int {
        return when (other) {
            is Miles -> value.compareTo(other.value)
            else -> throw IllegalArgumentException("Cannot compare Miles to $other")
        }
    }

    companion object {
        fun of(value: Double): Miles {
            if (value < 0) throw IllegalArgumentException("Miles must be non-negative")
            return Miles(value)
        }
    }
}

fun Double.miles(): Length = Miles.of(this)

internal data class Kilometers(val value: Double): Length {
    override fun compareTo(other: Length): Int {
        return when (other) {
            is Kilometers -> value.compareTo(other.value)
            else -> throw IllegalArgumentException("Cannot compare Kilometers to $other")
        }
    }

    companion object {
        fun of(value: Double): Kilometers {
            if (value < 0) throw IllegalArgumentException("Kilometers must be non-negative")
            return Kilometers(value)
        }
    }
}

fun Double.kilometers(): Length = Kilometers.of(this)

internal data class Meters(val value: Double): Length {
    override fun compareTo(other: Length): Int {
        return when (other) {
            is Meters -> value.compareTo(other.value)
            else -> throw IllegalArgumentException("Cannot compare Meters to $other")
        }
    }

    companion object {
        fun of(value: Double): Meters {
            if (value < 0) throw IllegalArgumentException("Meters must be non-negative")
            return Meters(value)
        }
    }
}

fun Double.meters(): Length = Meters.of(this)

internal data class Feet(val value: Double): Length {
    override fun compareTo(other: Length): Int {
        return when (other) {
            is Feet -> value.compareTo(other.value)
            else -> throw IllegalArgumentException("Cannot compare Feet to $other")
        }
    }

    companion object {
        fun of(value: Double): Feet {
            if (value < 0) throw IllegalArgumentException("Feet must be non-negative")
            return Feet(value)
        }
    }
}

fun Double.feet(): Length = Feet.of(this)
