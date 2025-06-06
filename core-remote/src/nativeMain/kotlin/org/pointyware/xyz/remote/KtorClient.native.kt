/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json

/**
 *
 */
actual fun getClient(): HttpClient {
    return HttpClient(CIO) {
        install(Resources)
        install(ContentNegotiation) { json() }
        defaultRequest {
            host = "api.xyz.pointyware.org"
            url { protocol = URLProtocol.HTTPS }
        }
        engine {

        }
    }
}
