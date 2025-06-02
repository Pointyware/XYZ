/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.data

import org.pointyware.xyz.api.BuildConfig
import java.sql.Connection
import java.sql.DriverManager

/**
 * Provides access to a PostgreSQL database.
 */
@Deprecated("Use PooledConnectionFactory instead", ReplaceWith("PooledConnectionFactory"))
class PostgresConnectionFactory {

    /**
     * Creates a new connection to the PostgreSQL database.
     */
    fun createConnection(
        host: String = BuildConfig.POSTGRES_HOST,
        port: Int = BuildConfig.POSTGRES_PORT.toInt(),
        db: String = BuildConfig.POSTGRES_DB,
        user: String = BuildConfig.POSTGRES_USER,
        password: String = BuildConfig.POSTGRES_PASSWORD
    ): Connection {
        return DriverManager.getConnection(
            "jdbc:postgresql://$host:$port/$db", user, password
        ).also { connection ->
            connection.autoCommit = false
        }
    }
}
