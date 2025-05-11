package org.pointyware.xyz.api.databases

import io.ktor.network.sockets.connect
import java.sql.Connection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
class RideDatabaseIntegrationTest {

    private lateinit var connection: Connection
    private lateinit var database: RideDatabase

    @BeforeTest
    fun setUp() {
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
        val authInitFile = javaClass.classLoader?.getResource("postgres/auth_init.sql")
        val rideInitFile = javaClass.classLoader?.getResource("postgres/rides_init.sql")

        authInitFile?.let {
            connection.prepareStatement(it.readText()).use { statement ->
                statement.execute()
            }
        } ?: throw IllegalStateException("Could not find auth_init.sql file")
        rideInitFile?.let {
            connection.prepareStatement(it.readText()).use { statement ->
                statement.execute()
            }
        } ?: throw IllegalStateException("Could not find rides_init.sql file")

        database = RideDatabaseImpl(
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
