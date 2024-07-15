/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.data.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataQualifier = named("data-scope")

/**
 * Collects all individual :core:data dependency bindings into a single module.
 */
fun coreDataModule() = module {
    single<DataDependencies> { KoinDataDependencies() }
    single<CoroutineScope>(qualifier = dataQualifier) { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    includes(
        repositoryModule()
    )
}

private fun repositoryModule() = module {
}
