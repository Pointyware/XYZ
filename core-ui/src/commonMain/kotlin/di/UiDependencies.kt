/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.core.ui.components.LoadingViewResources
import org.pointyware.xyz.core.ui.design.Resources
import org.pointyware.xyz.core.ui.Res
import org.pointyware.xyz.core.ui.loading_content_description
import org.pointyware.xyz.core.ui.tray_icon

/**
 *
 */
interface UiDependencies {
    val resources: Resources
}

class KoinUiDependencies : UiDependencies, KoinComponent {
    override val resources: Resources = get()
}

class EmptyTestUiDependencies : UiDependencies {
    override val resources: Resources = object : Resources {
        override val loadingViewResources: LoadingViewResources = object : LoadingViewResources {
            override val loadingIcon get() = Res.drawable.tray_icon
            override val loadingContentDescription get() = Res.string.loading_content_description
        }
    }
}
