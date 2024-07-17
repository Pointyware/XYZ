/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

data class ErrorState(
    val message: String,
    val dismissLabel: String
)

/**
 *
 */
@Composable
fun ErrorDialog(
    state: ErrorState,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column {
            Text(
                text = state.message
            )
            Button(
                onClick = onDismiss
            ) {
                Text(state.dismissLabel)
            }
        }
    }
}
