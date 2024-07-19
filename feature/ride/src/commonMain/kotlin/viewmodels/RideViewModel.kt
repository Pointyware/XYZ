/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.xyz.core.entities.Ride
import org.pointyware.xyz.core.viewmodels.MapViewModelImpl

/**
 *
 */
class RideViewModel(

): MapViewModelImpl() {

    private val mutableState = MutableStateFlow<RideUiState>(RideUiState.Idle)
    val state: StateFlow<RideUiState> get() = mutableState


}
