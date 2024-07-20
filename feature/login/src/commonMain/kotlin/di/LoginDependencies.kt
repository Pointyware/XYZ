/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.feature.login.viewmodels.AccountCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.login.viewmodels.ProfileViewModel

/**
 * Defines dependencies needed by all Login locations
 */
interface LoginDependencies {
    fun getAuthorizationViewModel(): AuthorizationViewModel
    fun getProfileViewModel(): ProfileViewModel
    fun getAccountCreationViewModel(): AccountCreationViewModel
}

class KoinLoginDependencies : LoginDependencies, KoinComponent {
    override fun getAuthorizationViewModel(): AuthorizationViewModel = get()
    override fun getProfileViewModel(): ProfileViewModel {
        return get()
    }

    override fun getAccountCreationViewModel(): AccountCreationViewModel {
        return get()
    }
}
