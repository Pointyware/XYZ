/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.common.BuildInfo
import org.pointyware.xyz.drive.data.DriverSettingsRepository
import org.pointyware.xyz.drive.data.DriverSettingsRepositoryImpl
import org.pointyware.xyz.drive.data.RideRepository
import org.pointyware.xyz.drive.data.RideRepositoryImpl
import org.pointyware.xyz.drive.data.TestDriverSettingsRepository
import org.pointyware.xyz.drive.data.TestRideRepository
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

    singleOf(::RideRepositoryImpl) { bind<RideRepository>() }
    singleOf(::DriverSettingsRepositoryImpl) { bind<DriverSettingsRepository>() }
}

fun featureDriveDataTestModule() = module {
    singleOf(::TestRideRepository) { bind<RideRepository>() }
    singleOf(::TestDriverSettingsRepository) { bind<DriverSettingsRepository>() }
}

fun featureDriveViewModelModule() = module {
    factoryOf(::DriveViewModel)
    factoryOf(::DriverSettingsViewModel)
}

fun featureDriveInteractorsModule() = module {
    factoryOf(::WatchRatedRequests)
}
