package org.pointyware.xyz.core.navigation.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.pointyware.xyz.core.navigation.StackNavigationController

/**
 *
 */
interface NavigationDependencies {
    fun getNavController(): StackNavigationController<Any, Any>
}

class KoinNavigationDependencies: NavigationDependencies, KoinComponent {
    override fun getNavController(): StackNavigationController<Any, Any> = get()
}
