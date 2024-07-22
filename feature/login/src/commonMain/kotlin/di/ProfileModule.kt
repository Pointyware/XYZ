/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import org.koin.dsl.module
import org.pointyware.xyz.feature.login.interactors.CreateDriverProfileUseCase
import org.pointyware.xyz.feature.login.interactors.CreateRiderProfileUseCase
import org.pointyware.xyz.feature.login.interactors.GetUserIdUseCase
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModelImpl
import org.pointyware.xyz.feature.login.viewmodels.ProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModelImpl

fun featureProfileModule() = module {
    single<ProfileDependencies> { KoinProfileDependencies() }

    factory<DriverProfileCreationViewModel> {
        DriverProfileCreationViewModelImpl(get<ProfileCreationViewModel>(), get<CreateDriverProfileUseCase>())
    }
    factory<RiderProfileCreationViewModel> {
        RiderProfileCreationViewModelImpl(get<ProfileCreationViewModel>(), get<CreateRiderProfileUseCase>(), get<GetUserIdUseCase>())
    }
}
