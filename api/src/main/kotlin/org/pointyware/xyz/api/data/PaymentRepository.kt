/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.data

import java.sql.Connection

/**
 *
 */
interface PaymentRepository {

}

/**
 *
 */
class PaymentRepositoryImpl(
    private val connection: Connection
): PaymentRepository {

}
