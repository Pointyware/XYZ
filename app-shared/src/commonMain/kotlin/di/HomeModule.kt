package org.pointyware.xyz.shared.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.shared.home.HomeUiStateMapper
import org.pointyware.xyz.shared.home.HomeViewModel
import org.pointyware.xyz.shared.home.HomeViewModelImpl
import org.pointyware.xyz.shared.home.homeRoute

/**
 * Defines productions bindings to satisfy interface requests.
 */
fun homeModule() = module {
    single<HomeDependencies> { KoinHomeDependencies() }

    single<HomeUiStateMapper> { HomeUiStateMapper }
    single<HomeViewModel> { HomeViewModelImpl() }

    factory<Any>(qualifier = named("home")) { homeRoute }
}
