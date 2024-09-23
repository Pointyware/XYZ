/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.local

import org.pointyware.xyz.feature.ride.entities.PaymentMethod

/**
 * A local store of payment methods.
 */
interface PaymentStore {
    fun getPaymentMethods(): List<PaymentMethod>
    fun savePaymentMethod(paymentMethod: PaymentMethod)
    fun removePaymentMethod(paymentMethod: PaymentMethod)
}

class PaymentStoreImpl(): PaymentStore {
    override fun getPaymentMethods(): List<PaymentMethod> {
        TODO("Not yet implemented")
    }

    override fun savePaymentMethod(paymentMethod: PaymentMethod) {
        TODO("Not yet implemented")
    }

    override fun removePaymentMethod(paymentMethod: PaymentMethod) {
        TODO("Not yet implemented")
    }
}

class TestPaymentStore(
    private val methods: MutableList<PaymentMethod> = mutableListOf()
): PaymentStore {

    override fun getPaymentMethods(): List<PaymentMethod> {
        return methods.toList()
    }

    override fun savePaymentMethod(paymentMethod: PaymentMethod) {
        methods.add(paymentMethod)
    }

    override fun removePaymentMethod(paymentMethod: PaymentMethod) {
        methods.remove(paymentMethod)
    }
}
