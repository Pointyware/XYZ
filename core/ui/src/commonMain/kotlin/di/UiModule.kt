package org.pointyware.xyz.core.ui.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uiQualifier = named("ui-scope")

/**
 *
 */
fun coreUiModule() = module {
    single<CoroutineScope>(qualifier = uiQualifier) { CoroutineScope(Dispatchers.Main + SupervisorJob()) }
}
