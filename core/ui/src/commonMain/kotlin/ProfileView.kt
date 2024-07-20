/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import org.pointyware.xyz.core.viewmodels.ProfileUiState

/**
 * Displays detailed information about a user profile.
 */
@Composable
fun ProfileView(
    state: ProfileUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AsyncImage(
            model = state.image.value,
            contentDescription = "Profile Image"
        )
        Text("Name: ${state.fullName.given}")
        Text(text = "Rating: ${state.rating}")
        Text(text = "Gender: ${state.gender}")
        Text(text = "Age: ${state.age}")
        Text(text = "Bio: ${state.bio}")
    }
}
