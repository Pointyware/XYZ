/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.remote.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.pointyware.xyz.core.remote.getClient

/**
 *
 */
fun coreRemoteModule() = module {
    single<HttpClient> { getClient() }
//    single { RemoteInteractor(get()) }
//    single { RemoteViewModel(get()) }
}
