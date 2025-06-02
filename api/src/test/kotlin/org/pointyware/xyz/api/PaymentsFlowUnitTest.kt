/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.api

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class PaymentsFlowUnitTest {

    @BeforeTest
    fun setUp() {
        // Setup server instance
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun create_client_payment_intent() {
        // Given a completed ride ID

        // When the rideId is posted to the payment intent endpoint

        // Then the client secret is returned
    }

    @Test
    fun complete_client_payment() {
        // Given a payment intent

        // When the payment is confirmed with the client secret

        // Then the payment is confirmed
    }
}
