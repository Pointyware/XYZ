/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import org.pointyware.xyz.core.viewmodels.DriverProfileUiState

interface DriverProfileCreationViewModel: ProfileCreationViewModel<DriverProfileUiState> {

}

/**
 * A view model for the rider profile creation view.
 */
class DriverProfileCreationViewModelImpl(
    private val delegate: ProfileCreationViewModel<DriverProfileUiState>
): DriverProfileCreationViewModel, ProfileCreationViewModel<DriverProfileUiState> by delegate {

}
