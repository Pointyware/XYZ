/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.android.di

import org.koin.dsl.module
import org.pointyware.painteddogs.android.ads.AdMobController
import org.pointyware.xyz.core.ui.ads.AdsController
import org.pointyware.xyz.shared.entities.SharedStringResources

/**
 *
 */
fun androidModule() = module {
    single<SharedStringResources> { AndroidStringResources() }

    factory<AdsController> { params -> AdMobController(params.get()) }
}
