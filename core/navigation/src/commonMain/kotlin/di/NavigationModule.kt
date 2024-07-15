package org.pointyware.xyz.core.navigation.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.xyz.core.navigation.StackNavigationController
import org.pointyware.xyz.core.navigation.StackNavigationControllerImpl

/**
 *
 */
fun coreNavigationModule() = module {
    single<NavigationDependencies> { KoinNavigationDependencies() }

    single<StackNavigationController<Any, Any>> {
        StackNavigationControllerImpl(get<Any>(qualifier = named("login")))
    }
}
