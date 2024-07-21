/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.desktop.di

import org.koin.dsl.module
import org.pointyware.xyz.core.ui.components.LoadingViewResources
import org.pointyware.xyz.core.ui.design.Resources
import org.pointyware.xyz.shared.di.appModule
import org.pointyware.xyz.shared.entities.SharedFileResources
import org.pointyware.xyz.shared.entities.SharedStringResources
import org.pointyware.xyz.shared.ui.SharedDrawableResources
import org.pointyware.xyz.shared.ui.SharedFontResources

val topLevelModule = module {
    includes(
        desktopModule(),
        appModule()
    )
}

/**
 * Provides the desktop module
 */
fun desktopModule() = module {
    single<SharedStringResources> { DesktopStringResources() }
    single<SharedFontResources> { DesktopFontResources() }
    single<SharedDrawableResources> { DesktopDrawableResources() }
    single<SharedFileResources> { DesktopFileResources() }

    single<Resources> { DesktopResources(get()) }
    // loading view resources

    factory<LoadingViewResources> { DesktopLoadingViewResources() }
}
