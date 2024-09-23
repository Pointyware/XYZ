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
import org.pointyware.xyz.feature.ride.data.RideRequestCache
import org.pointyware.xyz.feature.ride.data.RideRequestCacheImpl
import org.pointyware.xyz.feature.ride.data.RideRequestRepository
import org.pointyware.xyz.feature.ride.data.RideRequestRepositoryImpl
import org.pointyware.xyz.feature.ride.data.RideRequestService
import org.pointyware.xyz.feature.ride.data.RideRequestServiceImpl
import org.pointyware.xyz.feature.ride.data.TestRideRequestRepository
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
    singleOf(::RideRequestRepositoryImpl) { bind<RideRequestRepository>() }
    singleOf(::RideRequestCacheImpl) { bind<RideRequestCache>() }
    singleOf(::RideRequestServiceImpl) { bind<RideRequestService>() }

    singleOf(::PaymentRepositoryImpl) { bind<PaymentRepository>() }
    singleOf(::PaymentStoreImpl) { bind<PaymentStore>() }
}

fun featureRideDataTestModule() = module {
    single<RideRequestRepository> { TestRideRequestRepository(dataScope = get(qualifier = dataQualifier)) }

    single<PaymentStore> { TestPaymentStore(
        methods = mutableListOf(
            PaymentMethod(
                id = Uuid.v4(),
                lastFour = "3456",
                expiration = ExpirationDate(month = 12, year = 2024),
                cardholderName = "John Doe",
                paymentProvider = "Bisa"
            )
        )
    ) }
}
