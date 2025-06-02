/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uiQualifier = named("ui")

/**
 *
 */
fun coreUiModule() = module {
    single<CoroutineScope>(qualifier = uiQualifier) { CoroutineScope(Dispatchers.Main + SupervisorJob()) }
}
