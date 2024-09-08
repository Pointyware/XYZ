/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.pointyware.xyz.core.entities.ride.Accommodation
import org.pointyware.xyz.core.viewmodels.drive.AccommodationsSelectionUiState

/**
 *
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccommodationPicker(
    state: AccommodationsSelectionUiState,
    modifier: Modifier = Modifier,
    onAccommodationsSelected: (List<Accommodation>) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        FlowRow(
            modifier = modifier.semantics { contentDescription = "Selected Accommodations" }
        ) {
            state.selected.forEach { accommodation ->
                AccommodationChip(
                    value = accommodation,
                    onRemove = {
                        onAccommodationsSelected(state.selected - accommodation)
                    },
                )
            }
        }
        val availableAccommodations = listOf(Accommodation.AnimalFriendly, Accommodation.WheelchairAccess)
        val accommodations = availableAccommodations - state.selected.toSet()
        FlowRow(
            modifier = Modifier.semantics { contentDescription = "Available Accommodations" }
        ) {
            accommodations.forEach { accommodation ->
                AccommodationChip(
                    value = accommodation,
                    modifier = Modifier.clickable {
                        onAccommodationsSelected(state.selected + accommodation)
                    }
                )
            }
        }
    }
}
