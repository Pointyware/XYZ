/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.shadow
import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.ui.design.XyzTheme

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
            .shadow(XyzTheme.dimensions.spacerSmall, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
    ) {
        Box(
            modifier = Modifier
        ) {
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(XyzTheme.dimensions.paddingSmall),
            )
            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = XyzTheme.dimensions.paddingSmall, y = -XyzTheme.dimensions.paddingSmall)
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Remove")
            }
        }
    }
}
