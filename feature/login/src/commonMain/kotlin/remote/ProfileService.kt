/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import org.pointyware.xyz.core.entities.Profile

/**
 *
 */
interface ProfileService {
    fun login(email: String, password: String): Result<Profile>
}

class KtorProfileService(
    val client: HttpClient
): ProfileService {
    override fun login(email: String, password: String): Result<Profile> {
        TODO("Not yet implemented")
    }
}
