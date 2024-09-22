/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.entities.Name
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Individual
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.Gender
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
    single<DriverProfile> {
        DriverProfile( // TODO:
            id = Uuid.v4(),
            name = Name("", "", ""),
            gender = Gender.Man,
            business = Individual,
            picture = Uri.nullDevice,
            accommodations = emptySet()
        )
    }

    single<ProviderTripRepository> { TestProviderTripRepository(get(), get(), get(qualifier = dataQualifier)) }
    singleOf(::TestDriverSettingsRepository) { bind<DriverSettingsRepository>() }
}

fun featureDriveViewModelModule() = module {
    factoryOf(::ProviderDashboardViewModel)
    factoryOf(::DriverSettingsViewModel)
}

fun featureDriveInteractorsModule() = module {
    factoryOf(::WatchRatedRequests)
}
