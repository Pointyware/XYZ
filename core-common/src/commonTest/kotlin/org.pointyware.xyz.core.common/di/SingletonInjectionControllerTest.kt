/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common.di

import org.pointyware.xyz.core.common.Environment
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 *
 */
class SingletonInjectionControllerTest {

    private lateinit var controller: InjectionController

    @BeforeTest
    fun setUp() {
        controller = InjectionControllerImpl()
    }

    @Test
    fun getEnvironment() {
        // default value is "dev"
        assertEquals(Environment.DEV, controller.environment)
    }

    @Test
    fun setEnvironment() {
        repeat(2) {
            listOf(
                Environment.PROD, Environment.STAGE, Environment.DEV
            ).forEach { environment ->
                // given the environment is set to an accepted value
                controller.environment = environment
                // when the environment is retrieved
                // then the environment should be the value that was set
                assertEquals(environment, controller.environment)
            }
        }
    }

    @Test
    fun inject_dev_for_dev() {
        // given current environment is dev
        controller.environment = Environment.DEV
        // given a factory is provided for dev, stage, and prod environment
        val dependency = controller.inject(
            mapOf(
                Environment.DEV to { "dev" },
                Environment.STAGE to { "stage" },
                Environment.PROD to { "prod" }
            )
        )

        // when the value is returned
        // then the value should be the dev value
        assertEquals("dev", dependency)
    }

    @Test
    fun inject_stage_for_dev() {
        // given the current environment is dev
        controller.environment = Environment.DEV
        // given a factory is provided for stage, and prod environment
        val dependency = controller.inject(
            mapOf(
                Environment.STAGE to { "stage" },
                Environment.PROD to { "prod" }
            )
        )

        // when the value is returned
        // then the value should be the stage value
        assertEquals("stage", dependency)
    }

    @Test
    fun inject_prod_for_dev() {
        // given the current environment is dev
        controller.environment = Environment.DEV
        // given a factory is provided for prod environment
        val dependency = controller.inject(
            mapOf(
                Environment.PROD to { "prod" }
            )
        )

        // when the value is returned
        // then the value should be the prod value
        assertEquals("prod", dependency)
    }

    @Test
    fun inject_stage_for_stage() {
        // given the current environment is stage
        controller.environment = Environment.STAGE
        // given a factory is provided for dev, stage, and prod environment
        val dependency = controller.inject(
            mapOf(
                Environment.DEV to { "dev" },
                Environment.STAGE to { "stage" },
                Environment.PROD to { "prod" }
            )
        )

        // when the value is returned
        // then the value should be the stage value
        assertEquals("stage", dependency)
    }

    @Test
    fun inject_prod_for_stage() {
        // given the current environment is stage
        controller.environment = Environment.STAGE
        // given a factory is provided for dev, prod environment
        val dependency = controller.inject(
            mapOf(
                Environment.DEV to { "dev" },
                Environment.PROD to { "prod" }
            )
        )

        // when the value is returned
        // then the value should be the prod value
        assertEquals("prod", dependency)
    }

    @Test
    fun inject_prod_for_prod() {
        // given the current environment is prod
        controller.environment = Environment.PROD
        // given a factory is provided for dev, stage, prod environment
        val dependency = controller.inject(
            mapOf(
                Environment.DEV to { "dev" },
                Environment.STAGE to { "stage" },
                Environment.PROD to { "prod" }
            )
        )

        // when the value is returned
        // then the value should be the prod value
        assertEquals("prod", dependency)
    }

    @Test
    fun throw_exception_without_prod_in_prod() {
        // given the current environment is production
        controller.environment = Environment.PROD
        // given a factory is provided for dev, stage environment
        val result = kotlin.runCatching {
            controller.inject(
                mapOf(
                    Environment.DEV to { "dev" },
                    Environment.STAGE to { "stage" }
                )
            )
        }

        // when the value is returned
        // then an exception should be thrown
        assertTrue(result.isFailure)
    }
}
