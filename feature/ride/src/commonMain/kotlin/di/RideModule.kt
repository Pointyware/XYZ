/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.xyz.feature.ride.viewmodels.RideViewModel

/**
 *
 */
fun featureRideModule() = module {
    single<RideDependencies> { KoinRideDependencies() }

    includes(featureRideViewModelModule())
}

fun featureRideViewModelModule() = module {
    factoryOf(::RideViewModel)
}
