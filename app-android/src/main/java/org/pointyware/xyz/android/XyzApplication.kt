/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.android

import android.app.Application
import com.stripe.android.PaymentConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin
import org.pointyware.xyz.android.di.androidModule
import org.pointyware.xyz.ui.BuildConfig
import org.pointyware.xyz.ui.ads.AdsController
import org.pointyware.xyz.shared.di.setupKoin

/**
 * This is the production XYZ application; it performs production environment setup.
 */
class XyzApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        setupKoin(platformModule = androidModule())

        val koin = getKoin()
        val adsController = koin.get<AdsController> { parametersOf(this) }
        val startupScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        startupScope.launch {
            adsController.onAppStart()
        }

        PaymentConfiguration.init(
            applicationContext,
            BuildConfig.STRIPE_API_KEY
        )
    }
}
