package org.pointyware.xyz.api.services

import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class PaymentsServiceUnitTest {

    @BeforeTest
    fun setUp() {
        startKoin {

        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testCreatePaymentIntent() {

    }
}
