/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.data

import org.postgresql.ds.PGConnectionPoolDataSource
import java.sql.Connection
import javax.sql.PooledConnection

/**
 * Interface for a factory that creates pooled connections to a database.
 */
interface PooledConnectionFactory {
    /**
     * Creates a new connection to the database using the provided [user] and [password].
     */
    fun createConnection(user: String, password: String): Connection

    /**
     * Closes the connection pool and all connections in the pool.
     */
    fun close()
}

/**
 *  Creates a connection pool to a PostgreSQL database using the [PGConnectionPoolDataSource].
 *
 *  Each pool can create multiple connections with different users and passwords; however connecting
 *  to a different database requires the use of meta-commands which can be confusing when another
 *  controller wants to use the same connection expecting a different database - we then either
 *  need to switch databases before working with any connection, which is cumbersome, or
 *  we simply disallow the switching of databases and require the use of a different pool
 *  configured for the other database - the latter is the approach taken here.
 */
class PostgresPooledConnectionFactory(
    host: String,
    port: Int,
    db: String,
): PooledConnectionFactory {

    private val dataSource = PGConnectionPoolDataSource().apply {
        serverNames = arrayOf(host)
        portNumbers = intArrayOf(port)
        databaseName = db
    }

    private val connections: MutableList<PooledConnection> = mutableListOf()

    override fun createConnection(user: String, password: String): Connection {
        return dataSource.getPooledConnection(user, password).also {
            connections += it
        }.connection
    }

    override fun close() {
        connections.forEach { it.close() }
        connections.clear()
    }
}
