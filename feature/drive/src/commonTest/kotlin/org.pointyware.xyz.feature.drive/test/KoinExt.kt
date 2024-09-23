/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.drive.test

import org.koin.core.context.startKoin
import org.pointyware.xyz.core.data.di.coreDataModule
import org.pointyware.xyz.core.entities.di.coreEntitiesModule
import org.pointyware.xyz.core.interactors.di.coreInteractorsModule
import org.pointyware.xyz.core.navigation.di.coreNavigationModule
import org.pointyware.xyz.core.ui.di.coreUiModule
import org.pointyware.xyz.core.viewmodels.di.coreViewModelsModule
import org.pointyware.xyz.drive.di.featureDriveModule

/**
 * TODO: describe purpose/intent of KoinExt
 */
fun setupKoin() {
    startKoin {
        modules(
            coreUiModule(),
            coreViewModelsModule(),
            coreInteractorsModule(),
            coreDataModule(),
            coreEntitiesModule(),
            coreNavigationModule(),

            featureDriveModule(),
        )
    }
}
