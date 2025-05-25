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
