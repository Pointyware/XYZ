/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.newScope
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.pointyware.xyz.core.data.DefaultLifecycleController
import org.pointyware.xyz.core.data.LifecycleController
import kotlin.coroutines.CoroutineContext

val dataQualifier = named("data-scope")

/*
KoinScopeComponent exists to make using koin scopes easier
WindowComponent is a component specific to the window scope

The Android Component/Scope hierarchy works like so:
- Application/SingletonComponent
  - ServiceComponent
  - ActivityRetainedComponent
    - ViewModelComponent
    - ActivityComponent
      - ViewComponent
      - FragmentComponent
        - ViewWithFragmentComponent

A Desktop/Multiplatform Component/Scope hierarchy would look like this:
- Application/SingletonComponent
  - BackgroundComponent
  - WindowComponent
    - ViewModelComponent
    - ViewComponent
 */

class ApplicationComponent: KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }
}

class WindowComponent: KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }
}

class ViewModelComponent: KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }
}

class ViewComponent: KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }
}

/**
 * Collects all individual :core:data dependency bindings into a single module.
 */
fun coreDataModule(
    repositoryModule: Module = repositoryModule()
) = module {
    singleOf(::KoinDataDependencies) {
        bind<DataDependencies>()
    }
    single<CoroutineContext>(qualifier = dataQualifier) { Dispatchers.IO }
    single<CoroutineScope>(qualifier = dataQualifier) { CoroutineScope(
        get<CoroutineContext>(dataQualifier) + SupervisorJob()
    ) }

    scope<WindowComponent> {
        scopedOf(::DefaultLifecycleController) { bind<LifecycleController>() }
    }

    includes(
        repositoryModule
    )
}

private fun repositoryModule() = module {

}
