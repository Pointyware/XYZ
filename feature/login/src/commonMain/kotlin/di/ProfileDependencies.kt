/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.feature.login.viewmodels.AccountCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.ProfileViewModel

/**
 */
interface ProfileDependencies {
    fun getProfileViewModel(): ProfileViewModel
    fun getAccountCreationViewModel(): AccountCreationViewModel
}

class KoinProfileDependencies: ProfileDependencies, KoinComponent {

    override fun getProfileViewModel(): ProfileViewModel {
        return get()
    }

    override fun getAccountCreationViewModel(): AccountCreationViewModel {
        return get()
    }
}
