/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.viewmodels.DriverProfileUiState
import org.pointyware.xyz.core.viewmodels.emptyDriverProfile

interface DriverProfileCreationViewModel: ProfileCreationViewModel<DriverProfileUiState> {
    fun onCompanySelected(uuid: Uuid)
}

/**
 * A view model for the rider profile creation view.
 */
class DriverProfileCreationViewModelImpl(
    // TODO: interactors
): ProfileCreationViewModelImpl<DriverProfileUiState>(emptyDriverProfile), DriverProfileCreationViewModel {

    override fun onCompanySelected(uuid: Uuid) {
        TODO("Not yet implemented")
    }

    override fun onSubmit() {
        TODO("Not yet implemented")
    }
}
