package org.pointyware.xyz.api.services

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotEquals

class PaymentsServiceUnitTest {

    private lateinit var serviceUnderTest: PaymentsService

    @BeforeTest
    fun setUp() {
        serviceUnderTest = StripePaymentsService()
    }

    @AfterTest
    fun tearDown() {
    }

    @Test
    fun testCreatePaymentIntent() {
        val result = serviceUnderTest.createPaymentIntent("some-user-id", 100L)

        assertNotEquals("", result.getOrThrow())
    }
}
