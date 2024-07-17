/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import org.pointyware.xyz.feature.login.data.Authorization

/**
 * Exposes profile-related actions to be performed by a remote service.
 */
interface ProfileService {
}

class KtorProfileService(
    val client: HttpClient
): ProfileService {
}
