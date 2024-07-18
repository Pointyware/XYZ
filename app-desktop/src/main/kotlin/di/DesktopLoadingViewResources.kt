/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.desktop.di

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.pointyware.xyz.core.ui.components.LoadingViewResources
import org.pointyware.xyz.desktop.Res
import org.pointyware.xyz.desktop.loading_content_description
import org.pointyware.xyz.desktop.tray_icon

/**
 * Maps loading view resources to desktop actual resources.
 */
class DesktopLoadingViewResources: LoadingViewResources {
    override val loadingIcon: DrawableResource
        get() = Res.drawable.tray_icon
    override val loadingContentDescription: StringResource
        get() = Res.string.loading_content_description
}
