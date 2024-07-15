/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.desktop.di

import org.koin.dsl.module
import org.pointyware.xyz.core.ui.ui.LoadingViewResources
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

    // loading view resources

    factory<LoadingViewResources> { DesktopLoadingViewResources() }
}
