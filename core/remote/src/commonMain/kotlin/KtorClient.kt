/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.remote

import io.ktor.client.HttpClient

/**
 * Creates a ktor client for any platform.
 */
expect fun getClient(): HttpClient
