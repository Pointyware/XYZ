/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import kotlinx.datetime.Instant

/**
 * Represents a ride time that can be either immediate or scheduled.
 */
sealed class Time {
    data object Immediate : Time()
    data class Scheduled(val time: Instant) : Time()
}
