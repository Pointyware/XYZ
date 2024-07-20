/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import navigation.loginRoute
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.remote.getClient
import org.pointyware.xyz.feature.login.data.ProfileRepository
import org.pointyware.xyz.feature.login.data.ProfileRepositoryImpl
import org.pointyware.xyz.feature.login.interactors.CreateUserUseCase
import org.pointyware.xyz.feature.login.interactors.LoginUseCase
import org.pointyware.xyz.feature.login.local.AuthCache
import org.pointyware.xyz.feature.login.local.AuthCacheImpl
import org.pointyware.xyz.feature.login.local.ProfileCache
import org.pointyware.xyz.feature.login.local.ProfileCacheImpl
import org.pointyware.xyz.feature.login.remote.AuthService
import org.pointyware.xyz.feature.login.remote.KtorProfileService
import org.pointyware.xyz.feature.login.remote.ProfileService
import org.pointyware.xyz.feature.login.remote.TestAuthService
import org.pointyware.xyz.feature.login.remote.TestProfileService
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModelImpl

/**
 *
 */
fun featureLoginModule() = module {
    single<LoginDependencies> { KoinLoginDependencies() }

    single<Any>(qualifier = named("login")) { loginRoute }

    includes(
        featureLoginDataModule()
    )

    single<AuthorizationViewModel> { AuthorizationViewModelImpl(get<LoginUseCase>(), get<CreateUserUseCase>()) }
    single<LoginUseCase> { LoginUseCase(get<ProfileRepository>()) }
    single<CreateUserUseCase> { CreateUserUseCase(get<ProfileRepository>()) }
}

fun featureLoginDataModule() = module {
    single<ProfileRepository> { ProfileRepositoryImpl(
        get<AuthCache>(), get<AuthService>(), get<ProfileCache>(), get<ProfileService>(),
        get(dataQualifier)
    ) }
//    single<AuthService> { SimpleAuthService(get<HttpClient>()) }
    single<AuthService> { TestAuthService(
        users = mutableMapOf("foo@bar.com" to TestAuthService.UserEntry("password", Uuid.v4()))
    )}
    single<AuthCache> { AuthCacheImpl() }
    single<ProfileCache> { ProfileCacheImpl() }
//    single<ProfileService> { KtorProfileService(getClient()) }
    single<ProfileService> { TestProfileService() }
}
