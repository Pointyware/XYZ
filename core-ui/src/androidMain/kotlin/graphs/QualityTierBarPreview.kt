/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun QualityTierBarPreview() {
    Canvas(
        modifier = Modifier
            .height(20.dp)
            .fillMaxWidth()
    ) {
        drawQualityTierBar(
            floor = 2.5f,
            ceiling = 6.8f,
            segments = listOf(
                QualityTier(
                    lowestRate = 2.8f,
                    highestRate = 3.2f,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Green, Color.Yellow)
                    )
                ),
                QualityTier(
                    lowestRate = 3.2f,
                    highestRate = 4.4f,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Yellow, Color.Red)
                    )
                ),
                QualityTier(
                    lowestRate = 4.4f,
                    highestRate = 5.3f,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Red, Color.Magenta)
                    )
                ),
                QualityTier(
                    lowestRate = 5.5f,
                    highestRate = 6.5f,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Magenta, Color.Blue)
                    )
                )
            ),
        )
    }
}
