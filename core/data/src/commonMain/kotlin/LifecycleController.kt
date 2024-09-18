/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking

/**
 * Allows for lifecycle events to be handled by an Android Activity or Desktop Window.
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

/**
 * Events that can be emitted by a [LifecycleController].
 */
enum class LifecycleEvent {
    Create,
    Start,
    Resume,
    Pause,
    Stop
}

/**
 * Default implementation of [LifecycleController].
 */
class DefaultLifecycleController : LifecycleController {

    private val mutableLifecycleState = MutableSharedFlow<LifecycleEvent>(replay = 1)
    override val lifecycleState: SharedFlow<LifecycleEvent>
        get() = mutableLifecycleState.asSharedFlow()

    override fun onStart() = runBlocking {
        mutableLifecycleState.emit(LifecycleEvent.Start)
    }

    override fun onResume() = runBlocking {
        mutableLifecycleState.emit(LifecycleEvent.Resume)
    }

    override fun onPause() = runBlocking {
        mutableLifecycleState.emit(LifecycleEvent.Pause)
    }

    override fun onStop() = runBlocking {
        mutableLifecycleState.emit(LifecycleEvent.Stop)
    }
}
