/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data

import io.ktor.http.ContentType
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.core.data.di.ApplicationComponent
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
class KoinComponentsTest {

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    scope<ApplicationComponent> {

                    }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun nestedComponents() {
        val koin = getKoin()

        val appComponent = ApplicationComponent()


    }
}
