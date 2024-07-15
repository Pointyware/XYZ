package org.pointyware.xyz.desktop.di

import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify
import kotlin.test.Test

/**
 */
class KoinValidationTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun validate() {
        topLevelModule.verify()
    }
}
