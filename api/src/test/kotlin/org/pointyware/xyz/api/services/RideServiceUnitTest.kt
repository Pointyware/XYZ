package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.databases.PostgresConnectionFactory
import org.pointyware.xyz.api.databases.RideDatabase
import org.pointyware.xyz.api.databases.RideDatabaseImpl
import java.sql.Connection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class RideServiceUnitTest {

    private lateinit var connection: Connection
    private lateinit var database: RideDatabase
    private lateinit var unitUnderTest: RideService

    @BeforeTest
    fun setUp() {
        connection = PostgresConnectionFactory().createConnection()
        database = RideDatabaseImpl { connection }
        unitUnderTest = RideServiceImpl(database)
    }

    @AfterTest
    fun tearDown() {
        connection.close()
    }

    @Test
    fun create_a_ride() {

    }
}
