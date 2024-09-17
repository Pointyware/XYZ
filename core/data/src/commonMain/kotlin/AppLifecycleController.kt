/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data

/**
 * Allows for lifecycle events to be handled by the app.
 */
interface AppLifecycleController {
    fun onAppStart()
    fun onAppResume()
    fun onAppPause()
    fun onAppStop()
}
