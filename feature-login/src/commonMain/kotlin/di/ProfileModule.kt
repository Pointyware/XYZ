/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.io.files.Path
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.common.BuildInfo
import org.pointyware.xyz.core.common.di.ApplicationComponent
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.core.data.di.dataQualifier
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.local.di.testDirectory
import org.pointyware.xyz.feature.login.data.CompanyRepository
import org.pointyware.xyz.feature.login.data.CompanyRepositoryImpl
import org.pointyware.xyz.feature.login.data.ProfileRepository
import org.pointyware.xyz.feature.login.data.ProfileRepositoryImpl
import org.pointyware.xyz.feature.login.interactors.CreateDriverProfileUseCase
import org.pointyware.xyz.feature.login.interactors.CreateRiderProfileUseCase
import org.pointyware.xyz.feature.login.interactors.GetCompanyUseCase
import org.pointyware.xyz.feature.login.interactors.GetDriverProfileUseCase
import org.pointyware.xyz.feature.login.interactors.GetUserIdUseCase
import org.pointyware.xyz.feature.login.local.AuthCache
import org.pointyware.xyz.feature.login.local.CompanyCache
import org.pointyware.xyz.feature.login.local.ProfileCache
import org.pointyware.xyz.feature.login.local.ProfileCacheImpl
import org.pointyware.xyz.feature.login.remote.AuthService
import org.pointyware.xyz.feature.login.remote.CompanyService
import org.pointyware.xyz.feature.login.remote.KtorAuthService
import org.pointyware.xyz.feature.login.remote.KtorProfileService
import org.pointyware.xyz.feature.login.remote.ProfileService
import org.pointyware.xyz.feature.login.remote.fake.FakeAuthService
import org.pointyware.xyz.feature.login.remote.fake.FakeProfileService
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModelImpl
import org.pointyware.xyz.feature.login.viewmodels.ProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.ProfileCreationViewModelImpl
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModelImpl
import kotlin.coroutines.CoroutineContext

fun featureProfileModule() = module {
    singleOf(::KoinProfileDependencies) { bind<ProfileDependencies>() }

    includes(
        profileDataModule(),
        profileInteractorsModule(),
        profileViewModelModule(),
        profileLocalModule(),
        profileRemoteModule()
    )
}

private fun profileViewModelModule() = module {
    factoryOf(::ProfileCreationViewModelImpl) { bind<ProfileCreationViewModel>() }
    factoryOf(::DriverProfileCreationViewModelImpl) { bind<DriverProfileCreationViewModel>() }
    factoryOf(::RiderProfileCreationViewModelImpl) { bind<RiderProfileCreationViewModel>() }
}

private fun profileDataModule() = module {
    single<ProfileRepository> { ProfileRepositoryImpl(
        get<AuthCache>(), get<AuthService>(),
        get<ProfileCache>(), get<ProfileService>(),
        ioContext = get<CoroutineContext>(dataQualifier)
    ) }
    singleOf(::CompanyRepositoryImpl) { bind<CompanyRepository>() }
}

private fun profileInteractorsModule() = module {
    factoryOf(::CreateDriverProfileUseCase)
    factoryOf(::CreateRiderProfileUseCase)
    factoryOf(::GetUserIdUseCase)
    factoryOf(::GetCompanyUseCase)
    factoryOf(::GetDriverProfileUseCase)
}

private fun profileLocalModule() = module {
    singleOf(::ProfileCacheImpl) { bind<ProfileCache>() }
    singleOf(::CompanyCache)
}

private fun profileRemoteModule() = module {

    singleOf(::CompanyService)
    singleOf(::KtorProfileService) { bind<ProfileService>() }
}

fun profileRemoteTestModule() = module {
    single<ProfileService> {
        val profilePath = Path(get<Path>(qualifier = testDirectory), "profile.json")
        println("Using fake profile service with file: $profilePath")
        FakeProfileService(
            profileFile = profilePath,
            profiles = mutableMapOf(),
            json = get<Json>(),
            lifecycleController = get<ApplicationComponent>().scope.get<LifecycleController>(),
            dataContext = get<CoroutineContext>(qualifier = dataQualifier),
            dataScope = get<CoroutineScope>(qualifier = dataQualifier),
        )
    }
}
