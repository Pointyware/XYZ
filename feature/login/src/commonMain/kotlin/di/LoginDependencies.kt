package org.pointyware.xyz.feature.login.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel

/**
 * Defines dependencies needed by all Login locations
 */
interface LoginDependencies {
    fun getAuthorizationViewModel(): AuthorizationViewModel
}

class KoinLoginDependencies : LoginDependencies, KoinComponent {
    override fun getAuthorizationViewModel(): AuthorizationViewModel = get()
}