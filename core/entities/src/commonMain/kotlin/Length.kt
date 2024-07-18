/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

/**
 *
 */
interface Length: Comparable<Length> {

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
