/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
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

inline fun <reified T: Any> LocalDatabase.putData(key: String, data: T) {
    save(key, Encoder.json.encodeToString(data))
}

inline fun <reified T: Any> LocalDatabase.getData(key: String): T? {
    return load(key)?.let { Encoder.json.decodeFromString(it) }
}
