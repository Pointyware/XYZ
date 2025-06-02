/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.data

import io.ktor.server.sessions.SessionStorage

class RedisSessionStorage(
    // redisConnection
): SessionStorage {
    override suspend fun write(id: String, value: String) {
        TODO("Not yet implemented")
    }

    override suspend fun invalidate(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: String): String {
        TODO("Not yet implemented")
    }
}
