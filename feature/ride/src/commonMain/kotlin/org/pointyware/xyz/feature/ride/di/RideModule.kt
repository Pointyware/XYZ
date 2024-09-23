/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.feature.ride.data.PaymentRepository
import org.pointyware.xyz.feature.ride.data.PaymentRepositoryImpl
import org.pointyware.xyz.feature.ride.data.TripCache
import org.pointyware.xyz.feature.ride.data.TripCacheImpl
import org.pointyware.xyz.feature.ride.data.TripRepository
import org.pointyware.xyz.feature.ride.data.TripRepositoryImpl
import org.pointyware.xyz.feature.ride.data.TripService
import org.pointyware.xyz.feature.ride.data.TripServiceImpl
import org.pointyware.xyz.feature.ride.data.TestTripRepository
import org.pointyware.xyz.feature.ride.entities.ExpirationDate
import org.pointyware.xyz.feature.ride.entities.PaymentMethod
import org.pointyware.xyz.feature.ride.local.PaymentStore
import org.pointyware.xyz.feature.ride.local.PaymentStoreImpl
import org.pointyware.xyz.feature.ride.local.TestPaymentStore
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
    singleOf(::TripRepositoryImpl) { bind<TripRepository>() }
    singleOf(::TripCacheImpl) { bind<TripCache>() }
    singleOf(::TripServiceImpl) { bind<TripService>() }

    singleOf(::PaymentRepositoryImpl) { bind<PaymentRepository>() }
    singleOf(::PaymentStoreImpl) { bind<PaymentStore>() }
}

fun featureRideDataTestModule() = module {
    single<TripRepository> { TestTripRepository(dataScope = get(qualifier = dataQualifier)) }

    single<PaymentStore> { TestPaymentStore() }
}
