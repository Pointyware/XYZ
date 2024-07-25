/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.local

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 *
 */
object Encoder {
    val json = Json { prettyPrint = true }
}

inline fun <reified T: Any> LocalDatabase.putData(path: Path, data: T) {
    save(path, Encoder.json.encodeToString(data))
}

inline fun <reified T: Any> LocalDatabase.getData(path: Path): T? {
    return load(path)?.let { Encoder.json.decodeFromString(it) }
}
