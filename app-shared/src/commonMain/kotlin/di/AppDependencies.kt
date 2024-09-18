/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.shared.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.core.navigation.di.NavigationDependencies
import org.pointyware.xyz.core.ui.di.UiDependencies
import org.pointyware.xyz.drive.di.DriveDependencies
import org.pointyware.xyz.feature.login.di.LoginDependencies
import org.pointyware.xyz.feature.login.di.ProfileDependencies
import org.pointyware.xyz.feature.ride.di.RideDependencies

/**
 * Top level dependencies for the app
 */
interface AppDependencies {

    fun getNavigationDependencies(): NavigationDependencies
    fun getUiDependencies(): UiDependencies

    fun getLoginDependencies(): LoginDependencies
    fun getProfileDependencies(): ProfileDependencies
    fun getDriveDependencies(): DriveDependencies
    fun getRideDependencies(): RideDependencies
}

class KoinAppDependencies: AppDependencies, KoinComponent {

    override fun getNavigationDependencies(): NavigationDependencies = get()
    override fun getUiDependencies(): UiDependencies = get()

    override fun getLoginDependencies(): LoginDependencies = get()
    override fun getDriveDependencies(): DriveDependencies = get()
    override fun getRideDependencies(): RideDependencies = get()
    override fun getProfileDependencies(): ProfileDependencies = get()
}
