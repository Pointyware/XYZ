/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.local

import org.pointyware.xyz.drive.data.RideSearchResult

/**
 *
 */
interface RideCache {
    fun saveDestinations(query: String, searchResult: RideSearchResult)
    fun getDestinations(query: String): RideSearchResult?
    fun dropDestinations(query: String)
}

class RideCacheImpl(
    private val rideDao: RideDao
): RideCache {

    override fun saveDestinations(query: String, searchResult: RideSearchResult) {
        TODO("Not yet implemented")
    }

    override fun getDestinations(query: String): RideSearchResult? {
        TODO("Not yet implemented")
    }

    override fun dropDestinations(query: String) {
        TODO("Not yet implemented")
    }
}
