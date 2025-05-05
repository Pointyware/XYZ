package org.pointyware.xyz.api

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class RideAndPaymentE2ETest {

    @BeforeTest
    fun setUp() {

    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun happy_path() {
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
