/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.ui.design.XyzTheme
import org.pointyware.xyz.feature.login.viewmodels.ProfileCreationUiState

/**
 * Allows a user to specify their generic profile information.
 */
@Composable
fun ProfileCreationView(
    state: ProfileCreationUiState,
    modifier: Modifier = Modifier,
    onProfileImageSelected: (Uri) -> Unit,
    onGivenNameChange: (String) -> Unit,
    onMiddleNameChange: (String) -> Unit,
    onFamilyNameChange: (String) -> Unit,
    onBirthdateSelected: (Instant?) -> Unit,
    onGenderSelected: (Gender) -> Unit,
) {
    Column(
        modifier = modifier
    ) {

//        ImagePicker(
//            placeholder = state.image,
//            onImageSelected = { uri ->
//                onProfileImageSelected(uri)
//            }
//        ) {
//            AsyncImage(
//                model = it.value,
//                contentDescription = "Profile Image",
//            )
//        }
        TextField(
            value = state.fullName.given,
            label = { Text("Given Name") },
            singleLine = true,
            onValueChange = onGivenNameChange
        )
        TextField(
            value = state.fullName.middle,
            label = { Text("Middle Name") },
            singleLine = true,
            onValueChange = onMiddleNameChange
        )
        TextField(
            value = state.fullName.family,
            label = { Text("Family Name") },
            singleLine = true,
            onValueChange = onFamilyNameChange
        )

        val formatter = XyzTheme.dateFormatter
        Text(text = formatter.format(state.birthdate, "MMMM dd, yyyy"))
//        DatePicker( // TODO: get Locale
//            state = DatePickerState(
//                locale = CalendarLocale,
//            ),
//            onDateChange = onBirthdateChange,
//        )

//        GenderPicker(
//            value = state.gender,
//            onGenderSelected = onGenderSelected,
//        )
    }
}
