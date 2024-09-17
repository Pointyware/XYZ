/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data

import kotlinx.coroutines.flow.StateFlow

/**
 * Allows for lifecycle events to be handled by the app.
 */
interface AppLifecycleController {
    /**
     * A state flow of lifecycle events.
     */
    val lifecycleObservable: StateFlow<LifecycleEvent>

    /**
     * Called when the app is started.
     */
    fun onAppStart()

    /**
     * Called when the app is resumed.
     */
    fun onAppResume()

    /**
     * Called when the app is paused.
     */
    fun onAppPause()

    /**
     * Called when the app is stopped.
     */
    fun onAppStop()
}

enum class LifecycleEvent {
    Start,
    Resume,
    Pause,
    Stop
}
