/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Allows for lifecycle events to be handled by an Activity or Window.
 */
interface LifecycleController {
    /**
     * A flow of lifecycle events. Will replay the latest event on subscription.
     */
    val lifecycleState: SharedFlow<LifecycleEvent>

    /**
     * Called when the Window or Activity is started.
     */
    fun onAppStart()

    /**
     * Called when the Window or Activity is resumed.
     */
    fun onAppResume()

    /**
     * Called when the Window or Activity is paused.
     */
    fun onAppPause()

    /**
     * Called when the Window or Activity is stopped.
     */
    fun onAppStop()
}

enum class LifecycleEvent {
    Create,
    Start,
    Resume,
    Pause,
    Stop
}

class DefaultLifecycleController : LifecycleController {

    private val mutableLifecycleState = MutableStateFlow(LifecycleEvent.Create)
    override val lifecycleState: SharedFlow<LifecycleEvent>
        get() = mutableLifecycleState.asStateFlow()

    override fun onAppStart() {
        mutableLifecycleState.value = LifecycleEvent.Start
    }

    override fun onAppResume() {
        mutableLifecycleState.value = LifecycleEvent.Resume
    }

    override fun onAppPause() {
        mutableLifecycleState.value = LifecycleEvent.Pause
    }

    override fun onAppStop() {
        mutableLifecycleState.value = LifecycleEvent.Stop
    }
}
