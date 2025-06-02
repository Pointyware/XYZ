/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.design

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A simple interface to define the dimensions of a design system.
 */
interface Dimensions {
    val paddingSmall: Dp
    val paddingMedium: Dp
    val paddingLarge: Dp

    val spacerSmall: Dp
    val spacerMedium: Dp
    val spacerLarge: Dp
}

/**
 * A simple implementation of [Dimensions] for a multiplatform design system.
 */
object MultiplatformDimensions : Dimensions {
    override val paddingSmall: Dp = 16.dp
    override val paddingMedium: Dp = 24.dp
    override val paddingLarge: Dp = 36.dp

    override val spacerSmall: Dp = 8.dp
    override val spacerMedium: Dp = 16.dp
    override val spacerLarge: Dp = 24.dp
}
