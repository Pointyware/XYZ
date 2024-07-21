/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.components

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
import org.pointyware.xyz.core.ui.design.GrayedOut
import org.pointyware.xyz.core.ui.design.LocalResources

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
    val uiDependencies = LocalResources.current.loadingViewResources
    Box(
        modifier = modifier
            .background(color = GrayedOut)
    ) {
        Image(
            painter = painterResource(uiDependencies.loadingIcon),
            contentDescription = stringResource(uiDependencies.loadingContentDescription),
            modifier = Modifier.fillMaxSize()
        )
    }
}
