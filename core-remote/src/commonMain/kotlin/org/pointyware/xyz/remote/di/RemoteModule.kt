/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.remote.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.pointyware.xyz.remote.getClient

/**
 *
 */
fun coreRemoteModule() = module {
    single<HttpClient> { getClient() }
}
