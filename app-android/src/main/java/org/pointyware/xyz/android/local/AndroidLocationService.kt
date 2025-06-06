/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.android.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.LocationService

/**
 *
 */
class AndroidLocationService(

): LocationService {

    override fun start() {
        // https://developer.android.com/develop/sensors-and-location/location/request-updates
        TODO("Inject FusedLocationProvider service; start location updates")
    }

    override fun stop() {
        mutableState.value = LocationService.State.Stopped
    }

    private val mutableState = MutableStateFlow(LocationService.State.Stopped)
    override val state: StateFlow<LocationService.State>
        get() = mutableState.asStateFlow()
}
