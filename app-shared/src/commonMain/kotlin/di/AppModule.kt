/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.shared.di

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.coreDataModule
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.di.coreEntitiesModule
import org.pointyware.xyz.core.interactors.di.coreInteractorsModule
import org.pointyware.xyz.core.local.di.coreLocalModule
import org.pointyware.xyz.core.navigation.di.coreNavigationModule
import org.pointyware.xyz.core.remote.di.coreRemoteModule
import org.pointyware.xyz.core.ui.di.coreUiModule
import org.pointyware.xyz.core.viewmodels.di.coreViewModelsModule
import org.pointyware.xyz.drive.di.featureDriveModule
import org.pointyware.xyz.feature.login.di.featureLoginModule
import org.pointyware.xyz.feature.login.di.featureLoginRemoteModule
import org.pointyware.xyz.feature.login.di.featureProfileModule
import org.pointyware.xyz.feature.login.remote.AuthService
import org.pointyware.xyz.feature.login.remote.TestAuthService
import org.pointyware.xyz.feature.ride.di.featureRideModule


fun setupKoin(platformModules: List<Module>) {
    startKoin {
        modules(appModule())
        modules(platformModules)
    }

    // Temporary Test Setup - remove for production
    unloadKoinModules(listOf(
        featureLoginRemoteModule()
    ))
    loadKoinModules(module {
        single<AuthService> { TestAuthService(
            users = mutableMapOf("foo@bar.com" to TestAuthService.UserEntry("password", Uuid.v4()))
        ) }
    })
}

fun appModule(
    coreModule: Module = coreModule(),
    featureModule: Module = featureModule(),
): Module = module {
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
