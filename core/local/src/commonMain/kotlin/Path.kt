/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

/**
 * Represents a path to a file or directory across platforms.
 *
 * - Jvm: java.nio.file.Path
 * - iOS: NSUrl via NSFileManager
 * - Web: LocalStorage entries
 */
class Path(
    val value: String
) {
    val segments: List<String> = value.split(separator)

    companion object {
        const val separator = "/"
    }

    operator fun plus(other: Path): Path = Path(value + separator + other.value)
}
