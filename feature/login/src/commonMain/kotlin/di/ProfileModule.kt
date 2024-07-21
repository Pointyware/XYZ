/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.pointyware.xyz.core.viewmodels.DriverProfileUiState
import org.pointyware.xyz.core.viewmodels.RiderProfileUiState
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModelImpl
import org.pointyware.xyz.feature.login.viewmodels.ProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModelImpl

val driverQualifier = named("driver")
val riderQualifier = named("rider")

fun featureProfileModule() = module {
    single<ProfileDependencies> { KoinProfileDependencies() }

    factory<DriverProfileCreationViewModel> {
        DriverProfileCreationViewModelImpl(get<ProfileCreationViewModel<DriverProfileUiState>>(qualifier = driverQualifier))
    }
    factory<RiderProfileCreationViewModel> {
        RiderProfileCreationViewModelImpl(get<ProfileCreationViewModel<RiderProfileUiState>>(qualifier = riderQualifier))
    }


}
