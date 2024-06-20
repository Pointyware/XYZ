package org.pointyware.xyz.core.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

/**
 *
 */
actual fun getClient(): HttpClient {
    return HttpClient(OkHttp) {

    }
}
