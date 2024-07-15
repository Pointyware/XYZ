/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.android

import android.app.Application
import org.koin.core.context.startKoin
import org.pointyware.xyz.android.di.androidModule
import org.pointyware.xyz.shared.di.appModule

/**
 * This is the production XYZ application; it performs production environment setup.
 */
class PDApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                androidModule(),
                appModule()
            )
        }
    }
}
