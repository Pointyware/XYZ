/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

/**
 * Simulates a local data store, i.e. a cache or database.
 */
interface BaseTestLocalDataSource<T> {
    /**
     * Retrieves the data from the local data store.
     */
    suspend fun get(key: String): Result<T>
    /**
     * Stores the data in the local data store.
     */
    suspend fun put(key: String, data: T)
    /**
     * Removes the data from the local data store.
     */
    suspend fun remove(key: String)
}
