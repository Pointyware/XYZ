/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A generic loading UI state.
 */
sealed class LoadingUiState<T> {
    class Idle<T>: LoadingUiState<T>()
    class Loading<T> : LoadingUiState<T>()
    data class Success<T>(val value: T): LoadingUiState<T>()
    data class Error<T>(val message: String) : LoadingUiState<T>()
}

fun <T> MutableStateFlow<LoadingUiState<T>>.postError(error: Throwable) {
    value = LoadingUiState.Error(error.message ?: error.stackTraceToString())
}
