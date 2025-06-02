/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import org.pointyware.xyz.core.viewmodels.BriefProfileUiState

/**
 * Display brief information about a driver in a square-ish, collection-friendly format.
 * If the onRemove callback is passed, a button will be provided.
 */
@Composable
fun BriefProfileCollectionItem(
    state: BriefProfileUiState,
    modifier: Modifier = Modifier,
    onRemove: (()->Unit)? = null,
) {
    Row (
        modifier = modifier,
    ) {
        AsyncImage(
            model = state.image.value,
            contentDescription = "Profile Image"
        )
        Column {
            Text("Name: ${state.name}")
            Text(text = "Rating: ${state.rating}")
            onRemove?.let {
                Button(onClick = onRemove) {
                    Text("Remove")
                }
            }
        }
    }
}
