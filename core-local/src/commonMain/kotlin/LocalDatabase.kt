/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

import kotlinx.io.files.Path

/**
 * A simple interface to represent a local database.
 */
interface LocalDatabase {
    /**
     * Save the data to the given path, relative to the database root.
     */
    fun save(key: String, data: String)

    /**
     * Load the data from the given path, relative to the database root.
     */
    fun load(key: String): String?

    /**
     * Removes the data at the given path, relative to the database root.
     */
    fun delete(key: String)
}
