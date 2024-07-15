package org.pointyware.xyz.desktop.di

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
        topLevelModule.verify()
    }

    @Test
    fun checkKoinModules() {
        checkKoinModules(listOf(topLevelModule))
    }
}
