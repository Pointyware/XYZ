/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api.services

import org.pointyware.xyz.api.data.CommonRepository
import org.pointyware.xyz.api.data.CommonRepositoryImpl
import org.pointyware.xyz.api.data.PostgresConnectionFactory
import java.sql.Connection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class RideServiceUnitTest {

    private lateinit var connection: Connection
    private lateinit var commonRepository: CommonRepository
    private lateinit var unitUnderTest: RideService

    @BeforeTest
    fun setUp() {
        connection = PostgresConnectionFactory().createConnection()
        commonRepository = CommonRepositoryImpl(connection)
        unitUnderTest = RideServiceImpl(commonRepository)
    }

    @AfterTest
    fun tearDown() {
        connection.close()
    }

    @Test
    fun create_a_ride() {
//        database.rides.createRide()
    }
}
