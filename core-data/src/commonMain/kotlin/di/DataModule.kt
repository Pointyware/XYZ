/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data.di

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
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
    single<CoroutineExceptionHandler> { CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() } }
    single<CoroutineContext>(qualifier = dataQualifier) { Dispatchers.IO }
    single<CoroutineScope>(qualifier = dataQualifier) { CoroutineScope(
        get<CoroutineContext>(dataQualifier)
                + SupervisorJob()
                + get<CoroutineExceptionHandler>()
    ) }
    single<Json> { Json { isLenient = true } }

    includes(
        repositoryModule
    )
}

private fun repositoryModule() = module {

}
