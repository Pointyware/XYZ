/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.remote

import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import org.pointyware.xyz.remote.BuildConfig

internal const val HOST_URI = BuildConfig.API_HOST_URI
internal val HOST_PROTOCOL = if (BuildConfig.API_HOST_SECURE.toBoolean()) { URLProtocol.HTTPS } else { URLProtocol.HTTP }

/**
 * Creates a ktor client for any platform.
 */
expect fun getClient(): HttpClient
