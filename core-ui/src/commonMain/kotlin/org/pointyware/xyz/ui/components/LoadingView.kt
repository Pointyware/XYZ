/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pointyware.xyz.ui.design.GrayedOut
import org.pointyware.xyz.ui.Res
import org.pointyware.xyz.ui.loading_content_description
import org.pointyware.xyz.ui.tray_icon

interface LoadingViewResources {
    val loadingIcon: DrawableResource
    val loadingContentDescription: StringResource
}

/**
 * Displays a loading indicator over a grayed-out background.
 */
@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = GrayedOut)
    ) {
        Image(
            painter = painterResource(Res.drawable.tray_icon),
            contentDescription = stringResource(Res.string.loading_content_description),
            modifier = Modifier.fillMaxSize()
        )
    }
}
