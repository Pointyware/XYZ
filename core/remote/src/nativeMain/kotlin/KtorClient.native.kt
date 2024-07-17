/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

/**
 *
 */
actual fun getClient(): HttpClient {
    return HttpClient(CIO) {

    }
}
