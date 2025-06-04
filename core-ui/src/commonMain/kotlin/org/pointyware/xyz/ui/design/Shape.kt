/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.ui.design

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Defines geometry primitives and shapes for XYZ
 */

val shapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(32.dp)
)
