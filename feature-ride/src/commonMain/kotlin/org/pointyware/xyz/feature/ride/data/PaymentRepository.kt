/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.data

import org.pointyware.xyz.feature.ride.entities.PaymentMethod
import org.pointyware.xyz.feature.ride.local.PaymentStore

interface PaymentRepository {
    suspend fun getPaymentMethods(): Result<List<PaymentMethod>>
    suspend fun savePaymentMethod(paymentMethod: PaymentMethod): Result<Unit>
    suspend fun removePaymentMethod(paymentMethod: PaymentMethod): Result<Unit>
}

class PaymentRepositoryImpl(
    private val paymentStore: PaymentStore
) : PaymentRepository {

    override suspend fun getPaymentMethods(): Result<List<PaymentMethod>> = runCatching {
        paymentStore.getPaymentMethods()
    }

    override suspend fun savePaymentMethod(paymentMethod: PaymentMethod): Result<Unit> = runCatching {
        paymentStore.savePaymentMethod(paymentMethod)
    }

    override suspend fun removePaymentMethod(paymentMethod: PaymentMethod): Result<Unit> = runCatching {
        paymentStore.removePaymentMethod(paymentMethod)
    }
}
