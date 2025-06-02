/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlin.uuid.Uuid
import org.pointyware.xyz.core.viewmodels.drive.CompanyProfileUiState
import org.pointyware.xyz.core.viewmodels.drive.CompanySelectionUiState
import kotlin.uuid.ExperimentalUuidApi

/**
 * A search bar that allows the user to search for a company that shows up in a list or register a new company.
 */
@OptIn(ExperimentalUuidApi::class)
@Composable
fun CompanyPicker(
    state: CompanySelectionUiState,
    modifier: Modifier = Modifier,
    onSearchChange: (String)->Unit,
    onSelectCompany: (Uuid)->Unit,
    onCreateCompany: ()->Unit
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.clickable { isMenuOpen = true }
    ) {
        Row {
            var query by remember { mutableStateOf("") }
            TextField(
                value = query,
                label = { Text(text = "Company") },
                onValueChange = { query = it },
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSearchChange(query)
                        isMenuOpen = true
                    }
                )
            )

            IconButton(
                onClick = {
                    onSearchChange(query)
                    isMenuOpen = true
                }
            ) {
                Text(text = "Search")
            }
            IconButton(
                onClick = onCreateCompany
            ) {
                Text(text = "New")
            }
        }

        DropdownMenu(
            expanded = isMenuOpen,
            onDismissRequest = { isMenuOpen = false },
        ) {
            state.suggestions.forEach {
                Text(
                    text = it.name,
                    modifier = Modifier.clickable {
                        isMenuOpen = false
                        onSelectCompany(it.id)
                    }
                )
            }
        }
    }
}
