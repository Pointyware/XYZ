/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.ProfileViewModel
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel

/**
 */
interface ProfileDependencies {
    fun getProfileViewModel(): ProfileViewModel
    fun getRiderProfileCreationViewModel(): RiderProfileCreationViewModel
    fun getDriverProfileCreationViewModel(): DriverProfileCreationViewModel
}

class KoinProfileDependencies(

): ProfileDependencies, KoinComponent {

    override fun getProfileViewModel(): ProfileViewModel {
        return get()
    }

    override fun getRiderProfileCreationViewModel(): RiderProfileCreationViewModel {
        return get()
    }

    override fun getDriverProfileCreationViewModel(): DriverProfileCreationViewModel {
        return get()
    }
}
