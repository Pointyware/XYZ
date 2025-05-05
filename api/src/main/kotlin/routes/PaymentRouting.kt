package org.pointyware.xyz.api.routes

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.controllers.PaymentsController
import org.pointyware.xyz.core.data.dtos.CustomerInfo

fun Routing.payment() {
    val koin = getKoin()
    // Implemented per: https://docs.stripe.com/connect/direct-charges?platform=android#add-server-endpoint
    post("/payment-intent") {
        // Collect Call Information
        val customerInfo = call.receive<CustomerInfo>()

        // Create the Payment Intent for the Completed Ride
        val paymentsController = koin.get<PaymentsController>()

        val clientSecret = paymentsController.createPaymentIntent(customerInfo.id)

        val map = mapOf(
            "client_secret" to clientSecret,
        )
        call.respondNullable(map)
    }
}
