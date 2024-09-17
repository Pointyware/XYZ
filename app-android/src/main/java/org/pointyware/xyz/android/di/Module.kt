/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.android.di

import org.koin.dsl.module
import org.pointyware.xyz.android.ads.AdMobController
import org.pointyware.painteddogs.android.di.AndroidLoadingViewResources
import org.pointyware.painteddogs.android.di.AndroidResources
import org.pointyware.xyz.core.ui.ads.AdsController
import org.pointyware.xyz.core.ui.components.LoadingViewResources
import org.pointyware.xyz.core.ui.design.Resources
import org.pointyware.xyz.shared.entities.SharedStringResources

/**
 *
 */
fun androidModule() = module {
    single<SharedStringResources> { AndroidStringResources() }
    single<Resources> { AndroidResources(get()) }

    factory<AdsController> { params -> AdMobController(params.get()) }
    factory<LoadingViewResources> { AndroidLoadingViewResources() }
}
