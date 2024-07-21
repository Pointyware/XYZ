/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.pointyware.xyz.core.entities.profile.Disability

/**
 * Displays a single disability.
 */
@Composable
fun DisabilityChip(
    value: Disability,
    modifier: Modifier = Modifier,
    onRemove: ()->Unit
) {
    Surface(
        modifier = modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Box {
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.labelSmall
            )
            IconButton(
                onClick = onRemove,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Remove")
            }
        }
    }
}