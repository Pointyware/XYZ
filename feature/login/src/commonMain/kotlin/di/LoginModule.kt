package org.pointyware.xyz.feature.login.di

import org.koin.dsl.module
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModelImpl

/**
 *
 */
fun featureLoginModule() = module {
    single<AuthorizationViewModel> { AuthorizationViewModelImpl() }
}
