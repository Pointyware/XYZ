/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

/**
 *
 */
class LocalFileDatabase(
    path: Path
): LocalDatabase {
    override fun save(path: Path, data: String) {
        TODO("Not yet implemented")
    }

    override fun load(path: Path): String? {
        TODO("Not yet implemented")
    }

    override fun delete(path: Path) {
        TODO("Not yet implemented")
    }
}