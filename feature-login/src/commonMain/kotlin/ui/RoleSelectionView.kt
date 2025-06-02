/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.pointyware.xyz.core.entities.profile.Role
import org.pointyware.xyz.core.ui.design.XyzTheme

/**
 * Determines if the user intends to use the app as a rider or driver,
 */
@Composable
fun RoleSelectionView(
    modifier: Modifier = Modifier,
    onConfirm: (Role)->Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Welcome!")
        var selectedRole by remember { mutableStateOf<Role?>(null)}
        Row {
            Text(
                text = "Rider",
                modifier = Modifier
                    .padding(XyzTheme.dimensions.paddingSmall)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { selectedRole = Role.Rider }
            )
            Spacer(modifier = Modifier.width(XyzTheme.dimensions.spacerMedium))
            Text(
                text = "Driver",
                modifier = Modifier
                    .padding(XyzTheme.dimensions.paddingSmall)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { selectedRole = Role.Driver }
            )
        }

        Button(onClick = selectedRole?.let {
            { onConfirm(it) }
        } ?: {}) {
            Text(text = "Confirm")
        }
    }
}
