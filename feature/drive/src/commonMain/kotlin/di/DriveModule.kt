/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.drive.data.DriverSettingsRepository
import org.pointyware.xyz.drive.data.DriverSettingsRepositoryImpl
import org.pointyware.xyz.drive.data.ProviderTripRepository
import org.pointyware.xyz.drive.data.ProviderTripRepositoryImpl
import org.pointyware.xyz.drive.data.TestDriverSettingsRepository
import org.pointyware.xyz.drive.data.TestProviderTripRepository
import org.pointyware.xyz.drive.interactors.WatchRatedRequests
import org.pointyware.xyz.drive.viewmodels.ProviderDashboardViewModel
import org.pointyware.xyz.drive.viewmodels.DriverSettingsViewModel

/**
 */
fun featureDriveModule() = module {
    single<DriveDependencies> { KoinDriveDependencies() }

    includes(
        featureDriveDataModule(),
        featureDriveViewModelModule(),
        featureDriveInteractorsModule()
    )
}

fun featureDriveDataModule() = module {
    singleOf(::ProviderTripRepositoryImpl) { bind<ProviderTripRepository>() }
    singleOf(::DriverSettingsRepositoryImpl) { bind<DriverSettingsRepository>() }
}

fun featureDriveDataTestModule() = module {
    single<ProviderTripRepository> { TestProviderTripRepository(get(qualifier = dataQualifier)) }
    singleOf(::TestDriverSettingsRepository) { bind<DriverSettingsRepository>() }
}

fun featureDriveViewModelModule() = module {
    factoryOf(::ProviderDashboardViewModel)
    factoryOf(::DriverSettingsViewModel)
}

fun featureDriveInteractorsModule() = module {
    factoryOf(::WatchRatedRequests)
}
