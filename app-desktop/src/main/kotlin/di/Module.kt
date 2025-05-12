/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.desktop.di

import org.koin.dsl.module
import org.pointyware.xyz.core.local.org.pointyware.xyz.core.local.LocationService
import org.pointyware.xyz.desktop.local.DesktopLocationService

/**
 * Provides the desktop module
 */
fun desktopModule() = module {
    factory<LocationService> { DesktopLocationService() }
}
