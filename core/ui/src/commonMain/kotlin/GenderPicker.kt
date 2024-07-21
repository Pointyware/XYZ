/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
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
    onGenderSelected: (Gender)->Unit,
    modifier: Modifier = Modifier,
) {
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
