/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.shared.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.common.di.ApplicationComponent
import org.pointyware.xyz.core.common.di.ViewComponent
import org.pointyware.xyz.core.common.di.ViewModelComponent
import org.pointyware.xyz.core.common.di.WindowComponent
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

/**
 * Starts Koin with the standard application module, supported by the given platform module.
 * @param platformModule A module with platform-specific dependencies.
 */
fun setupKoin(platformModule: Module = module {}) {
    startKoin {
        modules(
            appModule(),
            platformModule
        )
    }
}

fun appModule(): Module = module {
    singleOf(::ApplicationComponent) // only one application

    scope<ApplicationComponent> {
        factoryOf(::WindowComponent) // multiple windows but each refers to scope application
    }
    scope<WindowComponent> {
        factoryOf(::ViewModelComponent) // multiple view models but each refers to scope window
        factoryOf(::ViewComponent) // multiple views but each refers to scope window
    }
    scope<ViewModelComponent> {
        // dependencies used only within view models
    }
    scope<ViewComponent> {
        // dependencies used only within views
    }

    factoryOf(::KoinAppDependencies) { bind<AppDependencies>() }
    includes(
        coreModule(),
        featureModule(),
    )
}

fun coreModule() = module {
    includes(
        coreEntitiesModule(),
        coreInteractorsModule(),
        coreViewModelsModule(),
        coreDataModule(),
        coreLocalModule(),
        coreRemoteModule(),
        coreNavigationModule(),
        coreUiModule(),
    )
}

fun featureModule() = module {
    includes(
        featureLoginModule(),
        featureProfileModule(),
        featureDriveModule(),
        featureRideModule(),
    )
}
