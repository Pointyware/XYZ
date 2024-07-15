/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Exposes a setter for the state of type [S], the reflected state, a consumer for events of
 * type [E], and a log of events.
 */
interface SimpleTestViewModel<out S, out E> {
    /**
     * Sets the view model state.
     */
    fun setState(state: @UnsafeVariance S)

    /**
     * Retrieves the view model state.
     */
    val simpleState: StateFlow<S>

    /**
     * Handles an event and adds it to the event log.
     */
    fun handleEvent(event: @UnsafeVariance E)

    /**
     * A complete list of all events that have been handled.
     */
    val eventLog: List<E>

    infix fun on(event: @UnsafeVariance E) = handleEvent(event)
}

/**
 * A simple implementation of [SimpleTestViewModel] that uses a [MutableStateFlow] to store state
 */
class SimpleTestViewModelImpl<out S, out E>(
    initialState: S
): SimpleTestViewModel<S, E> {
    override fun setState(state: @UnsafeVariance S) {
        _simpleState.value = state
    }

    private val _simpleState = MutableStateFlow<S>(initialState)
    override val simpleState: StateFlow<S>
        get() = _simpleState

    override fun handleEvent(event: @UnsafeVariance E) {
        _eventLog.add(event)
    }

    private val _eventLog = mutableListOf<E>()
    override val eventLog: List<E>
        get() = _eventLog
}
