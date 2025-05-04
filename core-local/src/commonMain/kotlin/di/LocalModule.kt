/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local.di

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.LocationService
import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.TestLocationService


val testDirectory = named("test-directory")

/**
 * Defines dependencies for the core local module.
 */
fun coreLocalModule() = module {
    // No :core:local dependencies for now
}

/**
 * Defines dependencies for the core local module during tests.
 */
fun coreLocalTestModule() = module {
    singleOf(::TestLocationService) { bind<LocationService>() }
    factory<Path>(qualifier = testDirectory) {
        Path("testing").also {
            SystemFileSystem.createDirectories(it)
        }
    }
}
