/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

import kotlinx.io.files.Path

/**
 *
 */
class LocalMemoryDatabase(
    val root: Path
): LocalDatabase {

    private val dataMap = mutableMapOf<Path, String>()

    override fun save(key: String, data: String) {
        dataMap[Path(root, key)] = data
    }

    override fun load(key: String): String? {
        return dataMap[Path(root, key)]
    }

    override fun delete(key: String) {
        dataMap.remove(Path(root, key))
    }
}

fun createLocalMemoryDatabase(path: Path): LocalDatabase {
    return LocalMemoryDatabase(path)
}
