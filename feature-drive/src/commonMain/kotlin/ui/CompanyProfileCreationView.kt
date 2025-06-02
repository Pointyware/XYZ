/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.data.Uri
import kotlin.uuid.Uuid
import org.pointyware.xyz.core.ui.BriefProfileCollectionItem
import org.pointyware.xyz.core.viewmodels.drive.CompanyProfileUiState
import kotlin.uuid.ExperimentalUuidApi

/**
 *
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalUuidApi::class)
@Composable
fun CompanyProfileCreationView(
    state: CompanyProfileUiState,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onBannerSelected: (Uri) -> Unit,
    onLogoSelected: (Uri) -> Unit,
    onTaglineChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onDriverAdd: () -> Unit,
    onDriverRemove: (Uuid) -> Unit,
    onContinue: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        // TODO: Implement ImageSelector
        // TODO: Preview Banner with ImageSelector onClick
        // TODO: Preview Logo with ImageSelector onClick
        TextField(
            value = state.name,
            label = { Text("Name") },
            onValueChange = onNameChange
        )
        TextField(
            value = state.tagline,
            label = { Text("Tagline") },
            onValueChange = onTaglineChange
        )
        TextField(
            value = state.description,
            label = { Text("Description") },
            onValueChange = onDescriptionChange
        )
        TextField(
            value = state.phoneNumber.sequence,
            label = { Text("Phone Number") },
            onValueChange = onPhoneNumberChange
        )
        Column {
            FlowRow {
                state.drivers.forEach { driver ->
                    BriefProfileCollectionItem(
                        state = driver,
                        onRemove = { onDriverRemove(driver.id) }
                    )
                }
            }
            Button(onClick = onDriverAdd) {
                Text("Add Driver")
            }
        }
        Button(onClick = onContinue) {
            Text("Continue")
        }
    }
}
