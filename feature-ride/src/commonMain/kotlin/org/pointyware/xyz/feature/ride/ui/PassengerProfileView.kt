/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.ui.ProfileView
import org.pointyware.xyz.core.viewmodels.ride.RiderProfileUiState

/**
 *
 */
@Composable
fun PassengerProfileView(
    state: RiderProfileUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        ProfileView(
            state = state.profile
        )
        Text(text = state.preferences)
        LazyColumn {
            items(state.disabilities.toList()) {
                Text(text = it.toString())
            }
        }
    }
}
