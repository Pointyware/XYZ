package org.pointyware.xyz.api.routes

import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.api.controllers.PaymentsController
import org.pointyware.xyz.core.data.CustomerInfo
import org.pointyware.xyz.core.entities.ride.Ride

fun Routing.payment() {

    // Implemented per: https://docs.stripe.com/connect/direct-charges?platform=android#add-server-endpoint
    post("/payment-intent") {

        val koin = getKoin()
        val rideRepository = koin.get<RideRepository>()
        val paymentsController = koin.get<PaymentsController>()

        val customerInfo = call.receive<CustomerInfo>()

        val ride = rideRepository.getRideById(customerInfo.id)
            .onSuccess {

            }
            .onFailure {

            }

        val paymentIntentCreateParams = PaymentIntentCreateParams.builder()
            .setCustomer(customerInfo.id) // customer ID
            .setAmount(1099L) // amount in cents
            .setCurrency("usd") // currency
            .build()

        val intent = PaymentIntent.create(paymentIntentCreateParams)

        val map = mapOf(
            "client_secret" to intent.clientSecret,
        )
        call.respondNullable(map)
    }
}

class RideRepository {
    fun getRideById(id: String): Result<Ride> {
        TODO("Not yet implemented")
    }
}
