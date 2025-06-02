/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.common

/**
 *
 */
interface Mapper<I, O> {
    fun map(input: I): O
}
