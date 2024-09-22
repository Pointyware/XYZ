/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local.org.pointyware.xyz.core.local

import kotlinx.coroutines.flow.StateFlow
import org.pointyware.xyz.core.entities.geo.Location

/**
 * Provides the current location of the driver and controls to start and stop location updates.
 */
interface LocationService {

    /**
     * Start location updates.
     */
    fun start()

    /**
     * Stop location updates.
     */
    fun stop()

    sealed interface State {
        val location: Location? get() = null
        val isRunning: Boolean

        data class Running(
            override val location: Location
        ) : State {
            override val isRunning = true
        }

        data object Stopped : State {
            override val isRunning = false
        }
    }

    val state: StateFlow<State>
}
