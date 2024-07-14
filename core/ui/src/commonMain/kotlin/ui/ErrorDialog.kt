package org.pointyware.xyz.core.ui.ui

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
