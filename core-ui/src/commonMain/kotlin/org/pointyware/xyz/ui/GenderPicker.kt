/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.profile.Gender

/**
 * Allows a user to select their gender.
 */
@Composable
fun GenderPicker(
    value: Gender,
    onGenderSelected: (Gender)->Unit,
    modifier: Modifier = Modifier,
) {
    Box {
        TextField(
            value = value.toString(),
            onValueChange = {},
            label = { Text("Gender") },
            readOnly = true,
        )

        var expanded by remember { mutableStateOf(false) }
        DropdownMenu(
            modifier = modifier.clickable { expanded = true },
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Gender.entries.forEach {
                DropdownMenuItem(
                    text = { Text(text = it.name) },
                    onClick = {
                        expanded = false
                        onGenderSelected(it)
                    }
                )
            }
        }
    }
}
