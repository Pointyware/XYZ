/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        FlowRow(modifier = modifier) {

        }
    }
}
