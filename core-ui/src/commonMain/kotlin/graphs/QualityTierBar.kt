/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.graphs

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Draws a bar representing the quality tiers available.
 *
 * The bar is divided into segments, each representing a different quality tier.
 *
 * Ideally, rates would increase with quality,
 */
fun DrawScope.drawQualityTierBar(
    segments: List<QualityTier>,
    floor: Float,
    ceiling: Float,
) {
    segments.forEach {
        val start = (it.lowestRate - floor) / (ceiling - floor) * size.width
        val end = (it.highestRate - floor) / (ceiling - floor) * size.width
        drawRect(
            brush = it.brush,
            topLeft = Offset(start, 0f),
            size = Size(end - start, size.height)
        )
    }
}

/**
 * Represents a quality tier in the quality tier bar. The given [brush] will
 * be used to draw the segment from [lowestRate] to [highestRate].
 */
data class QualityTier(
    val lowestRate: Float,
    val highestRate: Float,
    val brush: Brush
)
