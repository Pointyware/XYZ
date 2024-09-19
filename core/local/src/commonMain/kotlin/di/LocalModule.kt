/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local.di

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.core.common.BuildInfo


val testDirectory = named("test-directory")

/**
 * Defines dependencies for the core local module.
 */
fun coreLocalModule() = module {

    if (BuildInfo.isDebug) {
        factory<Path>(qualifier = testDirectory) {
            Path("testing").also {
                SystemFileSystem.createDirectories(it)
            }
        }
    }
}
