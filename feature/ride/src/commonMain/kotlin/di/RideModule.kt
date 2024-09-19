/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.common.BuildInfo
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.feature.ride.data.RideRequestCache
import org.pointyware.xyz.feature.ride.data.RideRequestCacheImpl
import org.pointyware.xyz.feature.ride.data.RideRequestRepository
import org.pointyware.xyz.feature.ride.data.RideRequestRepositoryImpl
import org.pointyware.xyz.feature.ride.data.RideRequestService
import org.pointyware.xyz.feature.ride.data.RideRequestServiceImpl
import org.pointyware.xyz.feature.ride.data.TestRideRequestRepository
import org.pointyware.xyz.feature.ride.viewmodels.RideViewModel

/**
 *
 */
fun featureRideModule() = module {
    single<RideDependencies> { KoinRideDependencies() }

    includes(
        featureRideViewModelModule(),
        featureRideDataModule()
    )
}

fun featureRideViewModelModule() = module {
    factoryOf(::RideViewModel)
}

fun featureRideDataModule() = module {
    if (BuildInfo.isDebug) {
        single<RideRequestRepository> { TestRideRequestRepository(dataScope = get(qualifier = dataQualifier)) }
    } else {
        singleOf(::RideRequestRepositoryImpl) {
            bind<RideRequestRepository>()
        }
    }
    singleOf(::RideRequestCacheImpl) {
        bind<RideRequestCache>()
    }
    singleOf(::RideRequestServiceImpl) {
        bind<RideRequestService>()
    }
}
