/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.viewmodels

import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.viewmodels.KoinViewModel
import org.pointyware.xyz.core.viewmodels.RiderProfileUiState
import org.pointyware.xyz.core.viewmodels.emptyRiderProfile

interface RiderProfileCreationViewModel: ProfileCreationViewModel<RiderProfileUiState> {
    fun onDisabilitiesSelected(disabilities: List<Disability>)
    fun onPreferencesChange(preferences: String)
}

/**
 * A view model for the rider profile creation view.
 */
class RiderProfileCreationViewModelImpl(
    // TODO: interactors
): ProfileCreationViewModelImpl<RiderProfileUiState>(emptyRiderProfile), RiderProfileCreationViewModel {

    override fun onDisabilitiesSelected(disabilities: List<Disability>) {

    }

    override fun onPreferencesChange(preferences: String) {

    }

    override fun onSubmit() {

    }
}
