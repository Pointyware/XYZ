package org.pointyware.xyz.feature.login.di

import navigation.loginRoute
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModelImpl

/**
 *
 */
fun featureLoginModule() = module {
    single<Any>(qualifier = named("login")) { loginRoute }

    single<AuthorizationViewModel> { AuthorizationViewModelImpl() }
}
