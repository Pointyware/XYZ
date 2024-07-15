/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.shared.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.coreDataModule
import org.pointyware.xyz.core.entities.di.coreEntitiesModule
import org.pointyware.xyz.core.interactors.di.coreInteractorsModule
import org.pointyware.xyz.core.local.di.coreLocalModule
import org.pointyware.xyz.core.navigation.di.coreNavigationModule
import org.pointyware.xyz.core.remote.di.coreRemoteModule
import org.pointyware.xyz.core.ui.di.coreUiModule
import org.pointyware.xyz.core.viewmodels.di.coreViewModelsModule
import org.pointyware.xyz.feature.login.di.featureLoginModule


fun appModule(): Module = module {
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
        featureLoginModule()
    )
}
