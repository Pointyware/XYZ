/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.ui.components.ErrorDialog
import org.pointyware.xyz.ui.components.ErrorState
import org.pointyware.xyz.ui.components.LoadingView
import org.pointyware.xyz.core.viewmodels.LoadingUiState

/**
 *
 */
@Composable
fun <T> LoadingResultView(
    state: LoadingUiState<T>,
    onSuccess: @Composable (T)->Unit,
    onDismiss: ()->Unit
) {
    when (state) {
        is LoadingUiState.Idle -> {
            // show nothing
        }
        is LoadingUiState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }
        is LoadingUiState.Success -> {
            onSuccess(state.value)
        }
        is LoadingUiState.Error -> {
            // show error message
            ErrorDialog(
                state = ErrorState(
                    message = state.message,
                    dismissLabel = "Dismiss"
                ),
                onDismiss = onDismiss
            )
        }
    }

}
