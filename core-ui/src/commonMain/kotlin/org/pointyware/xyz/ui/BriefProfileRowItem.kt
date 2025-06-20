/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import org.pointyware.xyz.core.viewmodels.BriefProfileUiState

/**
 * Display brief information about a profile in a row-like layout.
 */
@Composable
fun BriefProfileRowItem(
    state: BriefProfileUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        AsyncImage(
            model = state.image.value,
            contentDescription = "Profile Image"
        )
        Text("Name: ${state.name}")
        Text(text = "Rating: ${state.rating}")
    }
}
