/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.ui.ProfileCreationView
import org.pointyware.xyz.core.viewmodels.RiderProfileUiState

/**
 * Presents the user with a form to create their rider profile.
 */
@Composable
fun RiderProfileCreationView(
    state: RiderProfileUiState,
    modifier: Modifier = Modifier,
    onProfileImageSelected: (Uri)->Unit,
    onGivenNameChange: (String)->Unit,
    onMiddleNameChange: (String)->Unit,
    onFamilyNameChange: (String)->Unit,
    onBirthdateSelected: (Instant?)->Unit,
    onGenderSelected: (Gender)->Unit,
    onDisabilitiesSelected: (List<Disability>)->Unit,
    onPreferencesChange: (String)->Unit,
    onSubmit: ()->Unit,
) {
    Column(
        modifier = modifier,
    ) {
        ProfileCreationView(
            state = state,
            onProfileImageSelected = onProfileImageSelected,
            onGivenNameChange = onGivenNameChange,
            onMiddleNameChange = onMiddleNameChange,
            onFamilyNameChange = onFamilyNameChange,
            onBirthdateSelected = onBirthdateSelected,
            onGenderSelected = onGenderSelected,
        )

        // TODO: disabilities
        TextField(
            value = state.preferences,
            label = { Text("Phone Number") },
            onValueChange = onPreferencesChange
        )

        Button(onClick = onSubmit) {
            Text(text = "Submit")
        }
    }
}
