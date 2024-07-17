/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class ComponentState(
    val title: String?,
)

/**
 * A row that allows a user to select or change a date or clear the selection.
 * When a date is selected, the date is displayed in the row, with a button to clear the selection.
 * The user can also click on the date to change it.
 * When no date is selected, a button to select a date is displayed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Component(
    state: ComponentState,
    modifier: Modifier = Modifier,
    onTitleChanged: (String?) -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(text = state.title ?: "No title")
        Button(onClick = { onTitleChanged("New title") }) {
            Text(text = "Change title")
        }
    }
}
