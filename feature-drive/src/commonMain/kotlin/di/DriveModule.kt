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
import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.business.Individual
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.geo.meters
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.drive.data.DriverSettingsRepository
import org.pointyware.xyz.drive.data.DriverSettingsRepositoryImpl
import org.pointyware.xyz.drive.data.ProviderTripRepository
import org.pointyware.xyz.drive.data.ProviderTripRepositoryImpl
import org.pointyware.xyz.drive.data.TestDriverSettingsRepository
import org.pointyware.xyz.drive.data.TestProviderTripRepository
import org.pointyware.xyz.drive.interactors.WatchRatedRequests
import org.pointyware.xyz.drive.org.pointyware.xyz.drive.interactors.WatchProviderDistance
import org.pointyware.xyz.drive.viewmodels.DriverSettingsViewModel
import org.pointyware.xyz.drive.viewmodels.ProviderDashboardViewModel
import kotlin.uuid.ExperimentalUuidApi

/**
 */
fun featureDriveModule() = module {
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

@OptIn(ExperimentalUuidApi::class)
fun featureDriveDataTestModule() = module {
    single<DriverProfile> {
        DriverProfile( // TODO:
            id = Uuid.random(),
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
    factory<WatchProviderDistance> { WatchProviderDistance(get(), 100.0.meters())}
}
