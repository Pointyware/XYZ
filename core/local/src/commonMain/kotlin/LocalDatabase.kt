/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

import org.pointyware.xyz.core.entities.data.Uri

/**
 * A simple interface to represent a local database.
 */
interface LocalDatabase<T:Any> {
    /**
     * Save the data to the given path, relative to the database root.
     */
    fun save(path: Path, data: T)

    /**
     * Load the data from the given path, relative to the database root.
     */
    fun load(path: Path): T
}

expect fun <T:Any> createLocalDatabase(path: Path): LocalDatabase<T>
