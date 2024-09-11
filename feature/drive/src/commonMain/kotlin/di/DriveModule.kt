/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.drive.data.RideRepository
import org.pointyware.xyz.drive.data.RideRepositoryImpl
import org.pointyware.xyz.drive.viewmodels.DriveViewModel

/**
 */
fun featureDriveModule() = module {
    single<DriveDependencies> { KoinDriveDependencies() }

    includes(
        featureDriveDataModule(),
        featureDriveViewModelModule()
    )
}

fun featureDriveDataModule() = module {

    singleOf(::RideRepositoryImpl) { bind<RideRepository>() }
}

fun featureDriveViewModelModule() = module {
    factoryOf(::DriveViewModel)
}
