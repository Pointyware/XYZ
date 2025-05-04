/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
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

    override suspend fun getPaymentMethods(): Result<List<PaymentMethod>> {
        return try {
            Result.success(paymentStore.getPaymentMethods())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun savePaymentMethod(paymentMethod: PaymentMethod): Result<Unit> {
        return try {
            Result.success(paymentStore.savePaymentMethod(paymentMethod))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removePaymentMethod(paymentMethod: PaymentMethod): Result<Unit> {
        return try {
            Result.success(paymentStore.removePaymentMethod(paymentMethod))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
