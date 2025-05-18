package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.databases.PostgresConnectionFactory
import org.pointyware.xyz.api.databases.RiderRepository
import org.pointyware.xyz.api.databases.RiderRepositoryImpl
import java.sql.Connection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class RideServiceUnitTest {

    private lateinit var connection: Connection
    private lateinit var database: RiderRepository
    private lateinit var unitUnderTest: RideService

    @BeforeTest
    fun setUp() {
        connection = PostgresConnectionFactory().createConnection()
        database = RiderRepositoryImpl { connection }
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
