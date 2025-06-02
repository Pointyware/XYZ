/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

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
