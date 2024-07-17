/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.URLProtocol

/**
 *
 */
actual fun getClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(Resources)
        install(ContentNegotiation)
        defaultRequest {
            host = "api.xyz.pointyware.org"
            url { protocol = URLProtocol.HTTPS }
        }
        engine {
            config {

            }
        }
    }
}
