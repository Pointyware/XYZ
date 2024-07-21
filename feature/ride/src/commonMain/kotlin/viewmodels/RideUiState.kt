/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.viewmodels

/**
 *
 */
sealed interface RideUiState {

    data object Idle: RideUiState

    data class Search(
        val query: String = "",
        val suggestions: List<String>
    ): RideUiState


}
