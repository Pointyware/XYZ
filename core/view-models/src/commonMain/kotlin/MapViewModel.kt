/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.pointyware.xyz.core.entities.geo.LatLong

interface MapViewModel {
    val mapState: StateFlow<MapUiState>
}

/**
 *
 */
open class MapViewModelImpl(

): KoinViewModel(), MapViewModel {

    private val mutableMapState = MutableStateFlow(MapUiState(LatLong(0.0, 0.0)))
    override val mapState: StateFlow<MapUiState> get() = mutableMapState.asStateFlow()
}
