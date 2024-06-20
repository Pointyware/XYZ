package org.pointyware.xyz.android.di

import org.koin.dsl.module
import org.pointyware.xyz.shared.entities.SharedStringResources

/**
 *
 */
fun androidModule() = module {
    single<SharedStringResources> { AndroidStringResources() }
}
