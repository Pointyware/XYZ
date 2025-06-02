/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data.test

import kotlinx.coroutines.delay

/**
 * Defines behaviors to facilitate common testing scenarios.
 */
interface BaseTestRepository {
    suspend fun <T> stubResponse(delay: Long = 0, block: suspend () -> T): T {
        delay(delay)
        return block()
    }
}
