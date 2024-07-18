/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.remote

import org.pointyware.xyz.drive.data.RideSearchResult

/**
 *
 */
interface RideService {
    fun searchDestinations(query: String): Result<RideSearchResult>
}
