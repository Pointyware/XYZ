/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels.drive

import org.pointyware.xyz.core.entities.ride.Accommodation

/**
 * Associates just enough information about an accommodation to represent as a chip or in a list.
 */
data class AccommodationsSelectionUiState(
    val selected: List<Accommodation>
) {
    companion object {
        val empty: AccommodationsSelectionUiState = AccommodationsSelectionUiState(selected = emptyList())
    }
}
