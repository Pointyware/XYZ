/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

/**
 *
 */
class LocalMemoryDatabase(
    val root: Path
): LocalDatabase {

    private val dataMap = mutableMapOf<Path, String>()

    override fun save(path: Path, data: String) {
        dataMap[root + path] = data
    }

    override fun load(path: Path): String? {
        return dataMap[root + path]
    }

    override fun delete(path: Path) {
        dataMap.remove(root + path)
    }
}

fun createLocalMemoryDatabase(path: Path): LocalDatabase {
    return LocalMemoryDatabase(path)
}
