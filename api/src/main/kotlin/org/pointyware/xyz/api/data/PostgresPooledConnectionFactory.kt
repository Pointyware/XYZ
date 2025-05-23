package org.pointyware.xyz.api.data

import org.pointyware.xyz.api.BuildConfig
import org.postgresql.ds.PGConnectionPoolDataSource
import java.sql.Connection
import javax.sql.PooledConnection

/**
 *
 */
interface PooledConnectionFactory {
    fun createConnection(user: String, password: String): Connection
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

    private val connections: MutableMap<String, PooledConnection> = mutableMapOf()

    /**
     * Creates a new connection the configured PostgreSQL database using the given [user] and
     * [password].
     */
    override fun createConnection(user: String, password: String): Connection {
        return dataSource.getPooledConnection(user, password).also {
            connections[user] = it
        }.connection
    }

    /**
     * Closes the pool and all connections in the pool.
     */
    override fun close() {
        connections.values.forEach { it.close() }
        connections.clear()
    }
}
