/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.viewmodels.BriefProfileUiState

/**
 * Display brief information about a driver in a square-ish, collection-friendly format.
 */
@Composable
fun BriefProfileCollectionItem(
    state: BriefProfileUiState,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier,
    ) {
        Text("Image placeholder: ${state.image}") // TODO: replace with actual image
        Column {
            Text("Name: ${state.name}")
            Text(text = "Rating: ${state.rating}")
        }
    }
}
