/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.data.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.coroutines.CoroutineContext

/**
 */
interface DataDependencies {
    val ioCoroutineContext: CoroutineContext
}

class KoinDataDependencies: DataDependencies, KoinComponent {
    override val ioCoroutineContext: CoroutineContext
        get() = get()
}
