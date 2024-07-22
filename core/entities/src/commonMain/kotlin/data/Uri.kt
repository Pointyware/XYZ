/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.data

/**
 * Multiplatform implementation of URIs
 */
class Uri(
    val value: String
) {
    init {
        require(value.isNotBlank()) { "Uri cannot be blank" }
    }

    sealed class Protocol(val value: String) {
        data object File: Protocol("FILE")
        data object Http: Protocol("HTTP")
        data object Https: Protocol("HTTPS")
        data object Ftp: Protocol("FTP")
        class Other(value: String): Protocol(value)

        companion object {
            fun from(value: String): Protocol {
                val normalized = value.uppercase()
                listOf(Http, Https, Ftp).forEach {
                    if (normalized == it.value) {
                        return it
                    }
                }
                return Other(normalized)
            }
        }
    }
    val protocol: Protocol = Protocol.from(value.substringBefore(protocolDelimiter))
    val path: String = value.substringAfter(protocolDelimiter)

    companion object {
        const val protocolDelimiter = "://"

        val nullUri = Uri("file:///dev/null")
    }
}
