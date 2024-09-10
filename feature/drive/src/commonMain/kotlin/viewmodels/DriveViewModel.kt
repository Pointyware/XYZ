/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.viewmodels.MapViewModelImpl
import org.pointyware.xyz.drive.ui.DriveScreenState

/**
 *
 */
class DriveViewModel(

): MapViewModelImpl() {

    private val mutableState = MutableStateFlow<DriveScreenState>(DriveScreenState.Idle)
    val state: StateFlow<DriveScreenState> get() = mutableState

    fun onAccept(request: Uuid) {
        TODO("Send Acceptance; Update State on Success")
    }

    fun onReject(request: Uuid) {
        TODO("Send Rejection; Update State on Success")
    }

}
