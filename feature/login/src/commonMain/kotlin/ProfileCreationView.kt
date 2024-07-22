/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.ui.GenderPicker
import org.pointyware.xyz.core.ui.ImagePicker
import org.pointyware.xyz.feature.login.viewmodels.ProfileCreationUiState

/**
 * Allows a user to specify their generic profile information.
 */
@Composable
fun ProfileCreationView(
    state: ProfileCreationUiState,
    modifier: Modifier = Modifier,
    onProfileImageSelected: (Uri) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onFamilyNameChange: (String) -> Unit,
    onBirthdateSelected: (Instant?) -> Unit,
    onGenderSelected: (Gender) -> Unit,
    onGivenNameChange: (String) -> Unit,
) {
    Column(modifier = modifier) {

        ImagePicker(
            placeholder = state.image,
            onImageSelected = { uri ->
                onProfileImageSelected(uri)
            }
        ) {
            AsyncImage(
                model = it.value,
                contentDescription = "Profile Image",
            )
        }
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

        Text(text = state.birthdate.toString())
//        DatePicker( // TODO: get Locale
//            state = DatePickerState(
//                locale = CalendarLocale,
//            ),
//            onDateChange = onBirthdateChange,
//        )

        GenderPicker(
            onGenderSelected = onGenderSelected,
        )
    }
}