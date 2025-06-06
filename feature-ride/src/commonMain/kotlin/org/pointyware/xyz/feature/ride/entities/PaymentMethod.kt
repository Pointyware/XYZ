/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.entities

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
data class PaymentMethod(
    val id: Uuid,
//    val type: PaymentType,
    val lastFour: String,
    val expiration: ExpirationDate,
    val cardholderName: String,
    val paymentProvider: String,
//    val billingAddress: Address
)
