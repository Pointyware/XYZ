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
