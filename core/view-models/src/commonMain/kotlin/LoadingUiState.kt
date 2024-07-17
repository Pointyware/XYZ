/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

/**
 * A generic loading UI state.
 */
sealed class LoadingUiState<T> {
    data object Idle : LoadingUiState<Unit>()
    data object Loading : LoadingUiState<Unit>()
    data class Success<T>(val value: T): LoadingUiState<T>()
    data class Error(val message: String) : LoadingUiState<Unit>()
}
