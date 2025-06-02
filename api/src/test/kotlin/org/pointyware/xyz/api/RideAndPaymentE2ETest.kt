/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.sse.SSE
import io.ktor.client.plugins.sse.sse
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.pointyware.xyz.api.di.apiModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class RideAndPaymentE2ETest {

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(apiModule())
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    private fun ApplicationTestBuilder.createTestClient(): HttpClient {
        return createClient {
            install(SSE)
        }
    }

    @Test
    fun happy_path() = testApplication {
        application {
            commonModule()
            authModule()
            resourceModule()
        }

        val driverClient = createTestClient()
        val riderClient = createTestClient()

        runTest {
            launch {
                // driverClient actions
                driverClient.sse("foobar") {
                    incoming.collect { event ->
                        when (event.event) {

                        }
                    }
                }
            }
            launch {
                // riderClient actions
                riderClient.sse("driverPositions") {
                    incoming.collect { event ->
                        when (event.event) {

                        }
                    }
                }
            }
        }
        /*
        Given a driver and rider
        When the driver goes live with their asking rate
        Then the driver will appear on the rider's available drivers list
        When the rider hails a ride with their bidding rate
        Then the rider's bid will be added to the driver's order book
          And the rider's bid will be in the driver's top-pick list
        When the driver accepts the ride
        Then the rider will receive a notification that their ride has been accepted
          And the driver's information will be available to the rider
        When the driver arrives at the rider's location
        Then the driver will receive a notification that they have arrived
          And the rider will receive a notification that the driver has arrived
        When the driver starts the ride
        Then the driver will receive server confirmation that the ride has started
        When the driver arrives at the rider's destination
        Then the driver will receive a notification that they have arrived
          And the rider will receive a notification that the driver has arrived
        When the driver ends the ride
        Then the driver will receive server confirmation that the ride has ended
          And the rider will receive a notification that the ride has ended
          And the rider will be charged the final amount
         */
    }

    @Test
    fun driver_counter_offer() {
        /*
        Given a driver and rider
        When the driver goes live with their asking rate
        Then the driver will appear on the rider's available drivers list
        When the rider hails a ride with their bidding rate
        Then the rider's bid will be added to the driver's order book
          And the rider's bid will be in the driver's top-pick list
        When the driver offers a counter-offer
        Then the rider will receive a notification that their ride has been counter-offered
          And the driver's counter-offer will be available to the rider
        When the rider accepts the counter-offer
        Then the rider will receive a notification that their ride has been accepted
          And the driver's information will be available to the rider
          And the driver will receive a notification that their offer has been accepted
        When the driver arrives at the rider's location
        Then the driver will receive a notification that they have arrived
          And the rider will receive a notification that the driver has arrived
        When the driver starts the ride
        Then the driver will receive server confirmation that the ride has started
        When the driver arrives at the rider's destination
        Then the driver will receive a notification that they have arrived
          And the rider will receive a notification that the driver has arrived
        When the driver ends the ride
        Then the driver will receive server confirmation that the ride has ended
          And the rider will receive a notification that the ride has ended
          And the rider will be charged the final amount
         */
    }
}
