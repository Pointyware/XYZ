/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.painteddogs.android.di

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.pointyware.xyz.core.ui.components.LoadingViewResources
import org.pointyware.xyz.shared.Res
import org.pointyware.xyz.shared.loading_content_description
import org.pointyware.xyz.shared.tray_icon

/**
 *
 */
class AndroidLoadingViewResources(

): LoadingViewResources {

    override val loadingIcon: DrawableResource
        get() = Res.drawable.tray_icon
    override val loadingContentDescription: StringResource
        get() = Res.string.loading_content_description
}
