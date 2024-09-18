/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.shared.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.coreDataModule
import org.pointyware.xyz.core.entities.di.coreEntitiesModule
import org.pointyware.xyz.core.interactors.di.coreInteractorsModule
import org.pointyware.xyz.core.local.di.coreLocalModule
import org.pointyware.xyz.core.navigation.di.coreNavigationModule
import org.pointyware.xyz.core.remote.di.coreRemoteModule
import org.pointyware.xyz.core.ui.di.coreUiModule
import org.pointyware.xyz.core.viewmodels.di.coreViewModelsModule
import org.pointyware.xyz.drive.di.featureDriveModule
import org.pointyware.xyz.feature.login.di.featureLoginModule
import org.pointyware.xyz.feature.login.di.featureProfileModule
import org.pointyware.xyz.feature.ride.di.featureRideModule


fun setupKoin(platformModule: Module) {
    startKoin {
        modules(
            appModule(),
            platformModule
        )
    }
}

fun appModule(
    coreModule: Module = coreModule(),
    featureModule: Module = featureModule(),
): Module = module {
    factoryOf(::KoinAppDependencies) { bind<AppDependencies>() }
    includes(
        coreModule,
        featureModule,
    )
}

fun coreModule(
    entitiesModule: Module = coreEntitiesModule(),
    interactorsModule: Module = coreInteractorsModule(),
    viewModelsModule: Module = coreViewModelsModule(),
    dataModule: Module = coreDataModule(),
    localModule: Module = coreLocalModule(),
    remoteModule: Module = coreRemoteModule(),
    navigationModule: Module = coreNavigationModule(),
    uiModule: Module = coreUiModule(),
) = module {
    includes(
        entitiesModule,
        interactorsModule,
        viewModelsModule,
        dataModule,
        localModule,
        remoteModule,
        navigationModule,
        uiModule,
    )
}

fun featureModule(
    loginModule: Module = featureLoginModule(),
    profileModule: Module = featureProfileModule(),
    driveModule: Module = featureDriveModule(),
    rideModule: Module = featureRideModule(),
) = module {
    includes(
        loginModule,
        profileModule,
        driveModule,
        rideModule,
    )
}
