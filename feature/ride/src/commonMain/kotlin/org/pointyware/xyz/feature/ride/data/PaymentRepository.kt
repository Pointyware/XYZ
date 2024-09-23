/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

import org.pointyware.xyz.feature.ride.entities.PaymentMethod

interface PaymentRepository {
    suspend fun getPaymentMethods(): List<PaymentMethod>
}

class PaymentRepositoryImpl(

) : PaymentRepository {

    override suspend fun getPaymentMethods(): List<PaymentMethod> {
        TODO("Not yet implemented")
    }
}
