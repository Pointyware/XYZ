/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.io.files.Path
import kotlinx.serialization.json.Json
import navigation.loginRoute
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.common.BuildInfo
import org.pointyware.xyz.core.common.di.ApplicationComponent
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.local.di.testDirectory
import org.pointyware.xyz.core.navigation.di.homeQualifier
import org.pointyware.xyz.feature.login.data.ProfileRepository
import org.pointyware.xyz.feature.login.data.ProfileRepositoryImpl
import org.pointyware.xyz.feature.login.interactors.CreateUserUseCase
import org.pointyware.xyz.feature.login.interactors.LoginUseCase
import org.pointyware.xyz.feature.login.local.AuthCache
import org.pointyware.xyz.feature.login.local.AuthCacheImpl
import org.pointyware.xyz.feature.login.local.ProfileCache
import org.pointyware.xyz.feature.login.remote.AuthService
import org.pointyware.xyz.feature.login.remote.KtorAuthService
import org.pointyware.xyz.feature.login.remote.ProfileService
import org.pointyware.xyz.feature.login.remote.fake.FakeAuthService
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModel
import org.pointyware.xyz.feature.login.viewmodels.AuthorizationViewModelImpl
import kotlin.coroutines.CoroutineContext

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
    singleOf(::KtorAuthService) { bind<AuthService>() }
}

fun featureLoginRemoteTestModule() = module {
    single<AuthService> {
        val accountsFile = Path(get<Path>(qualifier = testDirectory), "accounts.json")
        println("Using fake auth service with file: $accountsFile")
        FakeAuthService(
            accountsFile = accountsFile,
            users = mutableMapOf(),
            json = get<Json>(),
            lifecycleController = get<ApplicationComponent>().scope.get<LifecycleController>(),
            dataContext = get<CoroutineContext>(qualifier = dataQualifier),
            dataScope = get<CoroutineScope>(qualifier = dataQualifier),
        )
    }
}

fun featureLoginLocalModule() = module {
    single<AuthCache> { AuthCacheImpl() }
}
