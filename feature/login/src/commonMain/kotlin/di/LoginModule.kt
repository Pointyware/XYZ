/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.di

import navigation.loginRoute
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.remote.getClient
import org.pointyware.xyz.feature.login.data.ProfileRepository
import org.pointyware.xyz.feature.login.data.ProfileRepositoryImpl
import org.pointyware.xyz.feature.login.interactors.CreateUserUseCase
import org.pointyware.xyz.feature.login.interactors.LoginUseCase
import org.pointyware.xyz.feature.login.local.AuthCache
import org.pointyware.xyz.feature.login.local.ProfileCache
import org.pointyware.xyz.feature.login.local.ProfileCacheImpl
import org.pointyware.xyz.feature.login.remote.AuthService
import org.pointyware.xyz.feature.login.remote.KtorProfileService
import org.pointyware.xyz.feature.login.remote.ProfileService
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModelImpl

/**
 *
 */
fun featureLoginModule() = module {
    single<LoginDependencies> { KoinLoginDependencies() }

    single<Any>(qualifier = named("login")) { loginRoute }

    single<AuthorizationViewModel> { AuthorizationViewModelImpl(get<LoginUseCase>(), get<CreateUserUseCase>()) }
    single<LoginUseCase> { LoginUseCase(get<ProfileRepository>()) }
    single<CreateUserUseCase> { CreateUserUseCase(get<ProfileRepository>()) }
    single<ProfileRepository> { ProfileRepositoryImpl(
        get<AuthCache>(), get<AuthService>(), get<ProfileCache>(), get<ProfileService>(),
        get(dataQualifier)
    ) }
    single<ProfileCache> { ProfileCacheImpl() }
    single<ProfileService> { KtorProfileService(getClient()) }
}
