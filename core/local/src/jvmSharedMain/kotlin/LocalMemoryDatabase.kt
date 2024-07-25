/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

import kotlinx.serialization.json.Json

/**
 * Implements [LocalDatabase] using the file indicated by [Path] as the root.
 */
class LocalMemoryDatabase(
    private val root: Path
) : LocalDatabase {

    override fun save(path: Path, data: String) {
        TODO("Not Yet Implemented")
    }

    override fun load(path: Path): String? {
        TODO("Not Yet Implemented")
    }

    override fun delete(path: Path) {
        TODO("Not Yet Implemented")
    }
}
