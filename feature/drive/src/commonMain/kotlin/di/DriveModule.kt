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
import org.pointyware.xyz.drive.data.DriverRideRepository
import org.pointyware.xyz.drive.data.DriverRideRepositoryImpl
import org.pointyware.xyz.drive.data.TestDriverSettingsRepository
import org.pointyware.xyz.drive.data.TestDriverRideRepository
import org.pointyware.xyz.drive.interactors.WatchRatedRequests
import org.pointyware.xyz.drive.viewmodels.DriveViewModel
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
    singleOf(::DriverRideRepositoryImpl) { bind<DriverRideRepository>() }
    singleOf(::DriverSettingsRepositoryImpl) { bind<DriverSettingsRepository>() }
}

fun featureDriveDataTestModule() = module {
    single<DriverRideRepository> { TestDriverRideRepository(get(qualifier = dataQualifier)) }
    singleOf(::TestDriverSettingsRepository) { bind<DriverSettingsRepository>() }
}

fun featureDriveViewModelModule() = module {
    factoryOf(::DriveViewModel)
    factoryOf(::DriverSettingsViewModel)
}

fun featureDriveInteractorsModule() = module {
    factoryOf(::WatchRatedRequests)
}
