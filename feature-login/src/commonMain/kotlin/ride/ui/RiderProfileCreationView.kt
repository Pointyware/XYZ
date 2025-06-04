/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.ride.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.ui.DisabilityPicker
import org.pointyware.xyz.ui.DisabilityPickerState
import org.pointyware.xyz.feature.login.ProfileCreationView
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationUiState

/**
 * Presents the user with a form to create their rider profile.
 */
@Composable
fun RiderProfileCreationView(
    state: RiderProfileCreationUiState,
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        ProfileCreationView(
            state = state.profile,
            onProfileImageSelected = onProfileImageSelected,
            onGivenNameChange = onGivenNameChange,
            onMiddleNameChange = onMiddleNameChange,
            onFamilyNameChange = onFamilyNameChange,
            onBirthdateSelected = onBirthdateSelected,
            onGenderSelected = onGenderSelected,
        )

        DisabilityPicker(
            state = DisabilityPickerState(state.disabilities.toList()),
            onUpdateSelected = onDisabilitiesSelected
        )
        TextField(
            value = state.preferences,
            label = { Text("Preferences") },
            singleLine = true,
            onValueChange = onPreferencesChange
        )

        Button(
            onClick = onSubmit,
            enabled = state.canSubmit
        ) {
            Text(text = "Submit")
        }
    }
}
