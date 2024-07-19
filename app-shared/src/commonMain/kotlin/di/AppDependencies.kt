/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.shared.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.drive.di.DriveDependencies
import org.pointyware.xyz.feature.login.di.LoginDependencies
import org.pointyware.xyz.feature.ride.di.RideDependencies

/**
 * Top level dependencies for the app
 */
interface AppDependencies {
    fun getLoginDependencies(): LoginDependencies

    fun getNavigationDependencies(): NavigationDependencies
    fun getDriveDependencies(): DriveDependencies
    fun getRideDependencies(): RideDependencies
}

fun getDependencies(): AppDependencies = KoinAppDependencies()

class KoinAppDependencies: AppDependencies, KoinComponent {
    override fun getLoginDependencies(): LoginDependencies {
        return get()
    }

    override fun getNavigationDependencies(): NavigationDependencies = get()
    override fun getDriveDependencies(): DriveDependencies {
        return get()
    }

    override fun getRideDependencies(): RideDependencies {
        return get()
    }
}
