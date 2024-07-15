package org.pointyware.xyz.desktop.di

import kotlin.test.Test

/**
 */
class KoinValidationTest {

    @Test
    fun validate() {
        topLevelModule.verify()
    }
}
