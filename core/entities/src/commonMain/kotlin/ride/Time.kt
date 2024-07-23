/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import kotlinx.datetime.Instant

/**
 *
 */
sealed class Time {
    data object Immediate : Time()
    data class Scheduled(val time: Instant) : Time()
}
