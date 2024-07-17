/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.shared.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.core.navigation.di.NavigationDependencies

/**
 * Top level dependencies for the app
 */
interface AppDependencies {
    fun getNavigationDependencies(): NavigationDependencies
}

fun getDependencies(): AppDependencies = KoinAppDependencies()

class KoinAppDependencies: AppDependencies, KoinComponent {
    override fun getNavigationDependencies(): NavigationDependencies = get()
}
