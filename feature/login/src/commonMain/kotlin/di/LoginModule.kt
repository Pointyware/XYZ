/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import navigation.loginRoute
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.navigation.di.homeQualifier
import org.pointyware.xyz.core.remote.getClient
import org.pointyware.xyz.feature.login.data.ProfileRepository
import org.pointyware.xyz.feature.login.data.ProfileRepositoryImpl
import org.pointyware.xyz.feature.login.interactors.CreateUserUseCase
import org.pointyware.xyz.feature.login.interactors.LoginUseCase
import org.pointyware.xyz.feature.login.local.AuthCache
import org.pointyware.xyz.feature.login.local.AuthCacheImpl
import org.pointyware.xyz.feature.login.local.ProfileCache
import org.pointyware.xyz.feature.login.remote.AuthService
import org.pointyware.xyz.feature.login.remote.KtorProfileService
import org.pointyware.xyz.feature.login.remote.ProfileService
import org.pointyware.xyz.feature.login.remote.TestAuthService
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModelImpl

/**
 *
 */
fun featureLoginModule() = module {
    single<LoginDependencies> { KoinLoginDependencies() }

    single<Any>(qualifier = homeQualifier) { loginRoute }

    includes(
        featureLoginDataModule()
    )

    singleOf(::AuthorizationViewModelImpl) {
        bind<AuthorizationViewModel>()
    }
    single<LoginUseCase> { LoginUseCase(get<ProfileRepository>()) }
    single<CreateUserUseCase> { CreateUserUseCase(get<ProfileRepository>()) }
}

private fun featureLoginDataModule() = module {
    single<ProfileRepository> { ProfileRepositoryImpl(
        get<AuthCache>(), get<AuthService>(), get<ProfileCache>(), get<ProfileService>(),
        get(dataQualifier)
    ) }

    includes(
        featureLoginRemoteModule(),
        featureLoginLocalModule()
    )
}

fun featureLoginRemoteModule() = module {
    single<AuthService> { SimpleAuthService(get<HttpClient>()) }
    single<ProfileService> { KtorProfileService(get<HttpClient>()) }
}

fun featureLoginLocalModule() = module {
    single<AuthCache> { AuthCacheImpl() }
}
