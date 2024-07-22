/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.ride.Accommodation
import org.pointyware.xyz.core.ui.CompanyPicker
import org.pointyware.xyz.feature.login.ProfileCreationView

/**
 * Provides a form for creating a driver profile.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverProfileCreationView(
    state: DriverProfileCreationUiState,
    modifier: Modifier = Modifier,
    onProfileImageSelected: (Uri)->Unit,
    onGivenNameChange: (String)->Unit,
    onMiddleNameChange: (String)->Unit,
    onFamilyNameChange: (String)->Unit,
    onBirthdateSelected: (Instant?)->Unit,
    onAccommodationsSelected: (List<Accommodation>)->Unit,
    onCompanySelected: (Uuid)->Unit,
    onGenderSelected: (Gender)->Unit,
    onSubmit: ()->Unit
) {
    Column(
        modifier = modifier
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

        CompanyPicker(
            value = state.company,
            onSelectCompany = { onCompanySelected(state.company.id) }
        )

        LazyColumn {
            items(state.accommodations.toList()) { accommodation ->
                Text(text = accommodation.toString())
            }
        }

        Button(onClick = onSubmit) {
            Text(text = "Submit")
        }
    }
}
