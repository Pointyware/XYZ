/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        Text("Image placeholder: ${state.image}") // TODO: replace with actual image
        Text("Name: ${state.fullName.given}")
        Text(text = "Rating: ${state.rating}")
        Text(text = "Gender: ${state.gender}")
        Text(text = "Age: ${state.age}")
        Text(text = "Bio: ${state.bio}")
    }
}
