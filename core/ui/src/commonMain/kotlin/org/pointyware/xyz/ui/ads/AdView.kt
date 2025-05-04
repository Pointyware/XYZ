/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.ui.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class AdViewState(
    val unit: String,
)

expect fun testAdViewState(): AdViewState

/**
 * Displays an ad.
 */
@Composable
expect fun AdView(
    state: AdViewState,
    modifier: Modifier = Modifier
)
