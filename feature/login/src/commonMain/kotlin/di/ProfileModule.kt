/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import kotlinx.io.files.Path
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.pointyware.xyz.core.common.BuildInfo
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
import org.pointyware.xyz.feature.login.remote.KtorProfileService
import org.pointyware.xyz.feature.login.remote.ProfileService
import org.pointyware.xyz.feature.login.remote.SimpleAuthService
import org.pointyware.xyz.feature.login.remote.TestAuthService
import org.pointyware.xyz.feature.login.remote.TestProfileService
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.DriverProfileCreationViewModelImpl
import org.pointyware.xyz.feature.login.viewmodels.ProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.ProfileCreationViewModelImpl
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModel
import org.pointyware.xyz.feature.login.viewmodels.RiderProfileCreationViewModelImpl
import kotlin.coroutines.CoroutineContext

fun featureProfileModule(
    dataModule: Module = profileDataModule(),
    interactorsModule: Module = profileInteractorsModule(),
    viewModelModule: Module = profileViewModelModule()
) = module {
    single<ProfileDependencies> { KoinProfileDependencies() }

    includes(
        dataModule,
        interactorsModule,
        viewModelModule
    )
}

private fun profileViewModelModule() = module {
    factory<ProfileCreationViewModel>() { ProfileCreationViewModelImpl() }
    factory<DriverProfileCreationViewModel> {
        DriverProfileCreationViewModelImpl(
            get<ProfileCreationViewModel>(), get<CreateDriverProfileUseCase>(),
            get<GetCompanyUseCase>()
        )
    }
    factory<RiderProfileCreationViewModel> {
        RiderProfileCreationViewModelImpl(
            get<ProfileCreationViewModel>(), get<CreateRiderProfileUseCase>(),
            get<GetUserIdUseCase>())
    }
}

fun profileDataModule() = module {
    single<ProfileRepository> { ProfileRepositoryImpl(
        get<AuthCache>(), get<AuthService>(),
        get<ProfileCache>(), get<ProfileService>(),
        ioContext = get<CoroutineContext>(dataQualifier)
    ) }

    single<CompanyRepository> { CompanyRepositoryImpl(get<CompanyCache>(), get<CompanyService>()) }
}

private fun profileInteractorsModule() = module {
    single<CreateDriverProfileUseCase> { CreateDriverProfileUseCase(get<ProfileRepository>()) }
    single<CreateRiderProfileUseCase> { CreateRiderProfileUseCase(get<ProfileRepository>()) }
    single<GetUserIdUseCase> { GetUserIdUseCase(get<AuthCache>()) }
    single<GetCompanyUseCase> { GetCompanyUseCase(get<CompanyRepository>()) }
    single<GetDriverProfileUseCase> { GetDriverProfileUseCase(get<ProfileRepository>()) }
}

private fun profileLocalModule() = module {
    single<ProfileCache> { ProfileCacheImpl() }
    single<CompanyCache> { CompanyCache() }
}

private fun profileRemoteModule() = module {

    singleOf(::CompanyService)

    if (BuildInfo.isDebug) {
        single<ProfileService> {
            val profilePath = Path(get<Path>(qualifier = testDirectory), "profile.json")
            TestProfileService(profilePath)
        }
        single<AuthService> {
            val accountsFile = Path(get<Path>(qualifier = testDirectory), "accounts.json")
            TestAuthService(
                accountsFile,
                users = mutableMapOf(
                    "foo@bar.com" to TestAuthService.UserEntry("password", Uuid.v4())
                )
            )
        }
    } else {
        single<ProfileService> { KtorProfileService(get()) }
        single<AuthService> { SimpleAuthService(get()) }
    }
}
