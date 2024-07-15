/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.ui.ProfileView
import org.pointyware.xyz.core.viewmodels.DriverProfileUiState

/**
 * A view that displays the driver profile: Image, name, gender, age, rating, bio, associated
 * company, and vehicle information.
 */
@Composable
fun DriverProfileView(
    state: DriverProfileUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        ProfileView(
            state = state
        )
        Text(text = "Associated Company: ${state.company.name}")
        LazyColumn {
            items(state.accommodations.toList()) { accommodation ->
                Text(text = "Accommodation: $accommodation")
            }
        }
    }
}
