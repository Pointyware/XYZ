package org.pointyware.xyz.api.databases

import java.sql.Connection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
class RiderRepositoryIntegrationTest {

    private lateinit var connection: Connection
    private lateinit var database: RiderRepository

    @BeforeTest
    fun setUp() {
        val adminConnection = PostgresConnectionFactory().createConnection(
            host = "localhost",
            port = 5432,
            db = "postgres",
            user = "postgres",
        )
        // Setup users, database, and privileges

        // Close the admin connection
        // Open admin connection to new database

        // Create tables on the database

        connection = PostgresConnectionFactory().createConnection(
            host = "localhost",
            port = 5432,
            db = "xyz",
            user = "xyz_android"
        )
        // drop the database (if any)
        connection.prepareStatement(
            "DROP DATABASE IF EXISTS xyz"
        ).use {
            it.execute()
        }
        val authInitFile = javaClass.classLoader?.getResource("postgres/init.sql")

        authInitFile?.let {
            connection.prepareStatement(it.readText()).use { statement ->
                statement.execute()
            }
        } ?: throw IllegalStateException("Could not find admin/init.sql file")

        database = RiderRepositoryImpl(
            connectionProvider = { connection },
        )
    }

    @AfterTest
    fun tearDown() {
        connection.close()
    }

    @Test
    fun driver_go_active() {

    }

    @Test
    fun rider_hail_ride() {

    }

    @Test
    fun driver_accept_ride() {

    }

    @Test
    fun driver_arrive_at_pickup() {

    }

    @Test
    fun driver_start_ride() {

    }

    @Test
    fun driver_arrive_at_destination() {

    }

    @Test
    fun driver_complete_ride() {

    }
}
