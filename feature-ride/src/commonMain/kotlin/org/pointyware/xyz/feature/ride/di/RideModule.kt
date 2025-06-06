/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.feature.ride.data.PaymentRepository
import org.pointyware.xyz.feature.ride.data.PaymentRepositoryImpl
import org.pointyware.xyz.feature.ride.data.TripCache
import org.pointyware.xyz.feature.ride.data.TripCacheImpl
import org.pointyware.xyz.feature.ride.data.TripRepository
import org.pointyware.xyz.feature.ride.data.TripRepositoryImpl
import org.pointyware.xyz.feature.ride.data.TripService
import org.pointyware.xyz.feature.ride.data.TripServiceImpl
import org.pointyware.xyz.feature.ride.data.TestTripRepository
import org.pointyware.xyz.feature.ride.local.PaymentStore
import org.pointyware.xyz.feature.ride.local.PaymentStoreImpl
import org.pointyware.xyz.feature.ride.local.FakePaymentStore
import org.pointyware.xyz.feature.ride.viewmodels.TripViewModel

/**
 *
 */
fun featureRideModule() = module {
    includes(
        featureRideViewModelModule(),
        featureRideDataModule()
    )
}

fun featureRideViewModelModule() = module {
    factoryOf(::TripViewModel)
}

fun featureRideDataModule() = module {
    singleOf(::TripRepositoryImpl) { bind<TripRepository>() }
    singleOf(::TripCacheImpl) { bind<TripCache>() }
    singleOf(::TripServiceImpl) { bind<TripService>() }

    singleOf(::PaymentRepositoryImpl) { bind<PaymentRepository>() }
    singleOf(::PaymentStoreImpl) { bind<PaymentStore>() }
}

fun featureRideDataTestModule() = module {
    single<TestTripRepository> { TestTripRepository(dataScope = get(qualifier = dataQualifier)) }
    single<TripRepository> { get<TestTripRepository>() }

    single { FakePaymentStore() }
    single<PaymentStore> { get<FakePaymentStore>() }
}
