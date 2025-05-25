package org.pointyware.xyz.api.data

import java.sql.Connection

/**
 *
 */
interface DriverRepository {

}

/**
 *
 */
class DriverRepositoryImpl(
    private val connection: Connection
): DriverRepository {

}
