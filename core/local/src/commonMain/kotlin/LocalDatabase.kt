/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

import org.pointyware.xyz.core.entities.data.Uri

/**
 * A simple interface to represent a local database.
 */
interface LocalDatabase {
    /**
     * Save the data to the given path, relative to the database root.
     */
    fun save(path: Path, data: String)

    /**
     * Load the data from the given path, relative to the database root.
     */
    fun load(path: Path): String?

    /**
     * Removes the data at the given path, relative to the database root.
     */
    fun delete(path: Path)
}

expect fun createLocalDatabase(path: Path): LocalDatabase
