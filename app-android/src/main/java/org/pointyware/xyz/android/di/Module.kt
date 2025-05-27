/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.android.di

import org.koin.dsl.module
import org.pointyware.xyz.android.ads.AdMobController
import org.pointyware.xyz.android.local.AndroidLocationService
import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.LocationService
import org.pointyware.xyz.ui.ads.AdsController
import org.pointyware.xyz.core.ui.components.LoadingViewResources
import org.pointyware.xyz.core.ui.design.Resources

/**
 *
 */
fun androidModule() = module {
    factory<AdsController> { params -> AdMobController(params.get()) }

    factory<LocationService> { AndroidLocationService() }
}
