/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.android

import android.app.Application
import com.stripe.android.PaymentConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin
import org.pointyware.xyz.android.di.androidModule
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.shared.di.setupKoin
import org.pointyware.xyz.ui.BuildConfig
import org.pointyware.xyz.ui.ads.AdsController

/**
 * This is the production XYZ application; it performs production environment setup.
 */
class XyzApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        setupKoin(platformModule = androidModule())

        val koin = getKoin()
        val adsController = koin.get<AdsController> { parametersOf(this) }
        val dataScope = koin.get<CoroutineScope>(qualifier = dataQualifier)
        dataScope.launch {
            adsController.onAppStart()
        }

        PaymentConfiguration.init(
            applicationContext,
            BuildConfig.STRIPE_API_KEY
        )
    }
}
