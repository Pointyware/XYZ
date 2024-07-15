package org.pointyware.xyz.desktop.di

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.check.checkKoinModules
import org.koin.test.verify.verify
import kotlin.test.Test

/**
 */
class KoinValidationTest {

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
