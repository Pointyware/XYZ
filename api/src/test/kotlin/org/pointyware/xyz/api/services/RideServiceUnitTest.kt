package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.databases.PostgresConnectionFactory
import java.sql.Connection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class RideServiceUnitTest {

    private lateinit var connection: Connection
    private lateinit var unitUnderTest: RideService

    @BeforeTest
    fun setUp() {
        connection = PostgresConnectionFactory().createConnection()
        unitUnderTest = PostgresRideService(connection)
    }

    @AfterTest
    fun tearDown() {
        connection.close()
    }

    @Test
    fun create_a_ride() {

    }
}
