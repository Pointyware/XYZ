/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.desktop.di

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.check.checkKoinModules
import org.koin.test.verify.verify
import org.pointyware.xyz.shared.di.appModule
import kotlin.test.Test

/**
 */
class KoinValidationTest { // TODO: create validation tests for other platforms

    val topLevelModule = module {
        includes(
            appModule(),
            desktopModule()
        )
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun verifyKoinConfiguration() {
        topLevelModule.verify(extraTypes = listOf(HttpClientEngine::class, HttpClientConfig::class))
    }

    @Test
    fun checkKoinModules() {
        checkKoinModules(listOf(topLevelModule))
    }
}
