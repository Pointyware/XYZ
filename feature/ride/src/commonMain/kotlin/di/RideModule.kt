/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.di

import org.koin.dsl.module

/**
 *
 */
fun featureRideModule() = module {
    single<RideDependencies> { KoinRideDependencies() }
}
