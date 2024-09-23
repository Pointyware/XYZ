/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.desktop.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.LocationService

/**
 *
 */
class DesktopLocationService(

): LocationService {
    override fun start() {
        TODO("Get desktop location service; start location updates")
    }

    override fun stop() {
        mutableState.value = LocationService.State.Stopped
    }

    private val mutableState = MutableStateFlow(LocationService.State.Stopped)
    override val state: StateFlow<LocationService.State>
        get() = mutableState.asStateFlow()
}
