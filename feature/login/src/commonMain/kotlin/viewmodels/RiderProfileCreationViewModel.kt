/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import org.pointyware.xyz.core.viewmodels.KoinViewModel
import org.pointyware.xyz.core.viewmodels.RiderProfileUiState

interface RiderProfileCreationViewModel: ProfileCreationViewModel<RiderProfileUiState> {

}

/**
 * A view model for the rider profile creation view.
 */
class RiderProfileCreationViewModelImpl(
    private val delegate: ProfileCreationViewModel<RiderProfileUiState>
): KoinViewModel(), RiderProfileCreationViewModel, ProfileCreationViewModel<RiderProfileUiState> by delegate {

}
