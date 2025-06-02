/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.max

@Preview
@Composable
fun OrderBookPreview() {
    val bids = listOf(1f, 2f, 3f, 4f, 5f).sortedDescending()
    val asks = listOf(6f, 7f, 8f, 9f, 10f).sorted()
    val maxNum = max(bids.size, asks.size)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    val width = size.width
                    drawRideHailBook(
                        bids = bids,
                        asks = asks,
                        bidWidth = width / maxNum / 2,
                        axisWidth = 8f,
                        priceCeiling = 10f
                    )
                }
            }
    )
}
