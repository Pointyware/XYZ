/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.di

import org.koin.dsl.module

/**
 */
fun featureDriveModule() = module {
    single<DriveDependencies> { KoinDriveDependencies() }

}
