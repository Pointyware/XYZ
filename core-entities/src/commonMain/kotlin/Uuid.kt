/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

import kotlinx.serialization.Serializable
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.random.Random

/**
 *
 */
private const val BYTE_COUNT = 16
private const val VERSION_INDEX = 6
private const val VERSION_MASK_INVERSE = 0x0F.toByte()
private const val VERSION_VALUE_4 = 0x40.toByte()

/**
 * Minimal implementation of Universal Uniform Identifier.
 */
@Serializable
data class Uuid(
    private val bytes: ByteArray
) {

    init {
        require(bytes.size == BYTE_COUNT) { "UUID must be $BYTE_COUNT bytes" }
    }

    operator fun get(index: Int): Byte = bytes[index]

    companion object {
        fun nil() = Uuid(ByteArray(BYTE_COUNT) { 0x0 })
        fun max() = Uuid(ByteArray(BYTE_COUNT) { 0xFF.toByte() })
        fun v4(): Uuid {
            val bytes = Random.nextBytes(BYTE_COUNT)
            bytes[VERSION_INDEX] = (bytes[VERSION_INDEX] and VERSION_MASK_INVERSE) or VERSION_VALUE_4
            return Uuid(bytes)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Uuid

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
