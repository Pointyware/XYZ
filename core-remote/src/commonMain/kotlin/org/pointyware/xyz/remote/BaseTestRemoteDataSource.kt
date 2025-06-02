/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.remote

/**
 * Simulates a remote data source, i.e. a network API.
 */
interface BaseTestRemoteDataSource<T> {
    /**
     * Retrieves the data from the remote data source.
     */
    suspend fun get(id: String): Result<T>
    /**
     * Stores the data in the remote data source.
     */
    suspend fun put(id: String, data: T)
    /**
     * Removes the data from the remote data source.
     */
    suspend fun delete(id: String)
}
