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
    fun onStart()

    /**
     * Called when the Window or Activity is resumed.
     */
    fun onResume()

    /**
     * Called when the Window or Activity is paused.
     */
    fun onPause()

    /**
     * Called when the Window or Activity is stopped.
     */
    fun onStop()
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

    override fun onStart() {
        mutableLifecycleState.value = LifecycleEvent.Start
    }

    override fun onResume() {
        mutableLifecycleState.value = LifecycleEvent.Resume
    }

    override fun onPause() {
        mutableLifecycleState.value = LifecycleEvent.Pause
    }

    override fun onStop() {
        mutableLifecycleState.value = LifecycleEvent.Stop
    }
}
