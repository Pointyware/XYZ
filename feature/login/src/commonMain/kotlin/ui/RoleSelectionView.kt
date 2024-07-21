/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.Role

/**
 * Determines if the user intends to use the app as a rider or driver,
 */
@Composable
fun RoleSelectionView(
    modifier: Modifier = Modifier,
    onConfirm: (Role)->Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Welcome!")
        var selectedRole by remember { mutableStateOf<Role?>(null)}
        Row(

        ) {
            Text(
                text = "Rider",
                modifier = Modifier.clickable { selectedRole = Role.Rider }
            )
            Text(
                text = "Driver",
                modifier = Modifier.clickable { selectedRole = Role.Driver }
            )
        }

        Button(onClick = selectedRole?.let {
            { onConfirm(it) }
        } ?: {}) {
            Text(text = "Confirm")
        }
    }
}
