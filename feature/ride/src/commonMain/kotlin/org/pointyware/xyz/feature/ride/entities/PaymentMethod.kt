/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.entities

/**
 *
 */
data class PaymentMethod(
    val id: String,
//    val type: PaymentType,
    val lastFour: String,
    val expiration: ExpirationDate,
    val cardholderName: String,
    val paymentProvider: String,
//    val billingAddress: Address
)
