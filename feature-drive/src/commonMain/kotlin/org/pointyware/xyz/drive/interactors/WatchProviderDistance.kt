/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive.org.pointyware.xyz.drive.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.pointyware.xyz.core.entities.geo.LatLong
import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.LocationService

class WatchProviderDistance(
    private val locationService: LocationService,
    private val acceptedDistance: Length
) {
    fun invoke(pickupLocation: LatLong): Flow<Boolean> = locationService.state.map {
        when (it) {
            is LocationService.State.Stopped -> false
            is LocationService.State.Running -> {
                val distance = it.location.distanceTo(pickupLocation)
                distance <= acceptedDistance
            }
        }
    }
}
