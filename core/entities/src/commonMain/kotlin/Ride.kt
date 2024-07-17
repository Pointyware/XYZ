/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

import kotlinx.datetime.Instant
import kotlin.time.Duration

enum class EndReason {
    Completed,
    RiderCanceled,
    RiderCanceledLate,
    DriverCanceled,
}

/**
 * Describes a ride in one of many possible states.
 *
 * 1. At the time a user is drafting a new ride request.
 * 2. At the time a user has posted a new ride request.
 * 3. At the time a driver has accepted a ride request.
 * 4. After a ride has been completed successfully.
 * 5. After a ride has been canceled by the rider or driver.
 */
interface Ride {

    val timePosted: Instant?
    val timeToStart: Instant?
    val timeAccepted: Instant?
    val timeEnded: Instant?
    val duration: Duration?
    val endReason: EndReason?

    interface Criteria {

    }
}
