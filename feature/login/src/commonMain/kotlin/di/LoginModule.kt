package org.pointyware.xyz.feature.login.di

import io.ktor.client.HttpClient
import navigation.loginRoute
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.core.remote.getClient
import org.pointyware.xyz.feature.login.data.UserRepository
import org.pointyware.xyz.feature.login.data.UserRepositoryImpl
import org.pointyware.xyz.feature.login.interactors.LoginUseCase
import org.pointyware.xyz.feature.login.local.UserCache
import org.pointyware.xyz.feature.login.local.UserCacheImpl
import org.pointyware.xyz.feature.login.remote.KtorUserService
import org.pointyware.xyz.feature.login.remote.UserService
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModelImpl

/**
 *
 */
fun featureLoginModule() = module {
    single<LoginDependencies> { KoinLoginDependencies() }

    single<Any>(qualifier = named("login")) { loginRoute }

    single<AuthorizationViewModel> { AuthorizationViewModelImpl() }
    single<LoginUseCase> { LoginUseCase(get<UserRepository>()) }
    single<UserRepository> { UserRepositoryImpl(get<UserCache>(), get<UserService>()) }
    single<UserCache> { UserCacheImpl() }
    single<UserService> { KtorUserService(getClient()) }
}
