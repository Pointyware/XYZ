/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.geo

import kotlin.math.PI

/**
 * Takes a longitude or latitude value in degrees and converts it to radians.
 */
fun Double.toRadians(): Double {
    return this * PI / 180
}
