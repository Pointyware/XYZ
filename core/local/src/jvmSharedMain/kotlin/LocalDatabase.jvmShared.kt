/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

actual fun createLocalDatabase(path: Path): LocalDatabase {
    return LocalMemoryDatabase(path)
}
