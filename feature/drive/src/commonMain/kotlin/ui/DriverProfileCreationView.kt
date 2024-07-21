/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.Gender
import org.pointyware.xyz.core.entities.Uri
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.viewmodels.DriverProfileUiState

/**
 * Provides a form for creating a driver profile.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverProfileCreationView(
    state: DriverProfileUiState,
    modifier: Modifier = Modifier,
    onProfileImageSelected: (Uri)->Unit,
    onGivenNameChange: (String)->Unit,
    onMiddleNameChange: (String)->Unit,
    onFamilyNameChange: (String)->Unit,
    onBirthdateChange: (Instant?)->Unit, // TODO: solve date picker lacking callback
    onCompanySelected: (Uuid)->Unit,
    onGenderSelected: (Gender)->Unit,
    onSubmit: ()->Unit
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage( // TODO: Implement ImagePicker, use for profile image
            model = state.image.value,
            contentDescription = "Profile Image",
        )
        TextField(
            value = state.fullName.given,
            label = { Text("Given Name") },
            onValueChange = onGivenNameChange
        )
        TextField(
            value = state.fullName.middle,
            label = { Text("Middle Name") },
            onValueChange = onMiddleNameChange
        )
        TextField(
            value = state.fullName.family,
            label = { Text("Family Name") },
            onValueChange = onFamilyNameChange
        )

//        DatePicker( // TODO: get Locale
//            state = DatePickerState(
//                locale = CalendarLocale,
//            ),
//            onDateChange = onBirthdateChange,
//        )

//        CompanyPicker( // TODO: implement
//            state = state.company,
//            onSelectCompany = { onCompanySelected(state.company.id) }
//        )

        LazyColumn {
            items(state.accommodations.toList()) { accommodation ->
                Text(text = accommodation.toString())
            }
        }

//        GenderSelector(
//            TODO: implement
//        )

        Button(onClick = onSubmit) {
            Text(text = "Submit")
        }
    }
}
