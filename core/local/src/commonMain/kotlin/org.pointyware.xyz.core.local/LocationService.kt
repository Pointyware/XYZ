/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local.org.pointyware.xyz.core.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.pointyware.xyz.core.entities.geo.LatLong
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
        val location: LatLong? get() = null
        val isRunning: Boolean

        data class Running(
            override val location: LatLong
        ) : State {
            override val isRunning = true
        }

        data object Stopped : State {
            override val isRunning = false
        }
    }

    val state: StateFlow<State>
}

class TestLocationService : LocationService {

    private var latestLocation: LatLong = LatLong(0.0, 0.0)
    fun setLocation(location: Location) {
        latestLocation = location.coordinates
        mutableState.update {
            when (it) {
                is LocationService.State.Running -> LocationService.State.Running(location.coordinates)
                is LocationService.State.Stopped -> it
            }
        }
        mutableState.value = LocationService.State.Running(location.coordinates)
    }

    override fun start() {
        mutableState.value = LocationService.State.Running(LatLong(0.0, 0.0))
    }

    override fun stop() {
        mutableState.value = LocationService.State.Stopped
    }

    private val mutableState = MutableStateFlow<LocationService.State>(LocationService.State.Stopped)
    override val state: StateFlow<LocationService.State> = mutableState.asStateFlow()
}
