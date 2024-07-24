/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

/**
 *
 */
class LocalFileDatabase<T:Any>(
    path: Path
): LocalDatabase<T> {
    override fun save(path: Path, data: T) {
        TODO("Not yet implemented")
    }

    override fun load(path: Path): T {
        TODO("Not yet implemented")
    }
}
