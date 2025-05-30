/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.sse.SSE
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

/**
 *
 */
actual fun getClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(SSE)
        install(Resources)
        install(ContentNegotiation) { json() }
        defaultRequest {
            host = HOST_URI
            url { protocol = HOST_PROTOCOL }
            contentType(ContentType.Application.Json)
        }
        engine {
            config {

            }
        }
    }
}
