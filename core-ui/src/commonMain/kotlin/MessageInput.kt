/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

/**
 *
 */
@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
) {
    TextField(
        value = "",
        onValueChange = {},
        modifier = modifier.semantics { contentDescription = "Message Input" }
    )
}
