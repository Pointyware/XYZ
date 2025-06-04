/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.xyz.feature.login.viewmodels

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.ride.Accommodation
import org.pointyware.xyz.ui.AccommodationPicker
import org.pointyware.xyz.ui.CompanyPicker
import org.pointyware.xyz.feature.login.ProfileCreationView
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Provides a form for creating a driver profile.
 */
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
    onCompanySearchChange: (String)->Unit,
    onCreateCompany: ()->Unit,
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

        AccommodationPicker(
            state = state.accommodations,
            onAccommodationsSelected = onAccommodationsSelected,
        )

        CompanyPicker(
            state = state.companySelection,
            onSelectCompany = { onCompanySelected(it) },
            onSearchChange = onCompanySearchChange,
            onCreateCompany = onCreateCompany
        )

        Button(
            onClick = onSubmit,
            enabled = state.canSubmit
        ) {
            Text(text = "Submit")
        }
    }
}
