/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.android

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin
import org.pointyware.xyz.android.di.androidModule
import org.pointyware.xyz.core.ui.ads.AdsController
import org.pointyware.xyz.shared.di.appModule

/**
 * This is the production XYZ application; it performs production environment setup.
 */
class XyzApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                androidModule(),
                appModule()
            )
        }

        val koin = getKoin()
        val adsController = koin.get<AdsController> { parametersOf(this) }
        val startupScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        startupScope.launch {
            adsController.onAppStart()
        }
    }
}
