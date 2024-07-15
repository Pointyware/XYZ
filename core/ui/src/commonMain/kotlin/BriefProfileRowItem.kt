/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        Text("Image placeholder: ${state.image}") // TODO: replace with actual image
        Text("Name: ${state.name}")
        Text(text = "Rating: ${state.rating}")
    }
}
