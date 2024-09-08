/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val dataQualifier = named("data-scope")

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

    includes(
        repositoryModule
    )
}

private fun repositoryModule() = module {

}
