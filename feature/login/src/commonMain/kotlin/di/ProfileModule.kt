/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.pointyware.xyz.core.data.di.dataQualifier
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
import org.pointyware.xyz.feature.login.remote.ProfileService
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

    single<ProfileCache> { ProfileCacheImpl() }
    single<ProfileService> { TestProfileService() }

    single<CompanyRepository> { CompanyRepositoryImpl(get<CompanyCache>(), get<CompanyService>()) }
    single<CompanyCache> { CompanyCache() }
    single<CompanyService> { CompanyService() }
}

private fun profileInteractorsModule() = module {
    single<CreateDriverProfileUseCase> { CreateDriverProfileUseCase(get<ProfileRepository>()) }
    single<CreateRiderProfileUseCase> { CreateRiderProfileUseCase(get<ProfileRepository>()) }
    single<GetUserIdUseCase> { GetUserIdUseCase(get<AuthCache>()) }
    single<GetCompanyUseCase> { GetCompanyUseCase(get<CompanyRepository>()) }
    single<GetDriverProfileUseCase> { GetDriverProfileUseCase(get<ProfileRepository>()) }
}
