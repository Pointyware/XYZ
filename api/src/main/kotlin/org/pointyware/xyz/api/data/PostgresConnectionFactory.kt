package org.pointyware.xyz.api.data

import org.pointyware.xyz.api.BuildConfig
import java.sql.Connection
import java.sql.DriverManager

/**
 * Provides access to a PostgreSQL database.
 */
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
//        val dataSource = PGConnectionPoolDataSource() // TODO: try pooled connections instead of individual connections
//
//        dataSource.serverNames = arrayOf(host)
//        dataSource.databaseName = db
//        dataSource.portNumbers = intArrayOf(port)
//        dataSource.getConnection(user, password)

        return DriverManager.getConnection(
            "jdbc:postgresql://$host:$port/$db", user, password
        ).also { connection ->
            connection.autoCommit = false
        }
    }
}
