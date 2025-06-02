/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.data

import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

/**
 * Write the given string to the file at this path.
 */
fun Path.writeText(accountsJsonString: String) {
    val dataBuffer = Buffer().apply { write(accountsJsonString.toByteArray(Charsets.UTF_8)) }
    SystemFileSystem.sink(this).use { sink ->
        sink.write(dataBuffer, dataBuffer.size)
        sink.flush()
        sink.close()
    }
}
