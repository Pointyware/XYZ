/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.app

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
class RideShareUiTest {
    @BeforeTest
    fun setUp() {

    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun create_driver_profile() {

    }

    @Test
    fun create_passenger_profile() {

    }

    @Test
    fun request_ride() {
        /*
        TODO: Test ride creation; given a driver and passenger exist
          1. Login as rider
          2. Select Create Ride => Open Ride Creation Screen
          3. Fill Ride Details: Start, End, Time, Seats
          4. Confirm => Open Ride Hail Screen; =NetworkError=> Show Error
         */
    }

    @Test
    fun cancel_ride_before_start() {
        /*
        TODO: Test cancelling a ride; given a driver and passenger exist and passenger has requested a ride
          1. On Ride Hail Screen
          2. Select Cancel => Open Ride Cancel Confirmation Dialog
          3. Confirm => Open Home Screen; =NetworkError=> Show Error
         */
    }

    @Test
    fun accept_passenger() {
        /*
        TODO: Test accepting a passenger; given a driver and passenger exist and passenger has requested a ride
          1. Login as driver
          2. Select Ride Requests => Open Ride Requests Screen
          3. Wait for passenger request
          3. Select Accept => Open Ride Details Screen; =NetworkError=> Show Error
         */
    }

    @Test
    fun passenger_cancels_before_start() {
        /*
        TODO: Test passenger canceling a ride; given a driver and passenger exist and passenger has requested a ride
          1. On Ride Details Screen; Requested Ride is visible
          2. When passenger cancels => Requested Ride is removed
         */
    }
}
