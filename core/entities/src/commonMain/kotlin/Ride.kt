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
sealed interface Ride {

    /**
     * The time the ride was posted to the system.
     */
    val timePosted: Instant?

    /**
     * The time the ride is scheduled to start.
     */
    val timeToStart: Instant?

    /**
     * The time the ride was accepted by a driver.
     */
    val timeAccepted: Instant?

    /**
     * The time the driver arrived at the pickup location.
     */
    val timeArrived: Instant?

    /**
     * The time the rider was picked up by a driver.
     */
    val timeStarted: Instant?

    /**
     * The time the ride was completed or canceled.
     */
    val timeEnded: Instant?

    /**
     * The duration of the ride, from [timeStarted] to [timeEnded].
     */
    val duration: Duration?
        get() = timeStarted?.let { start ->
            timeEnded?.minus(start)
        }

    /**
     * The reason the ride ended: completion or cancellation.
     */
    val endReason: EndReason?

    /**
     * The planned route of the ride.
     */
    val plannedRoute: Route?

    /**
     * The actual route taken during the ride.
     */
    val actualRoute: Route?

    val driver: DriverProfile?
    val rider: RiderProfile?

    /**
     * Filter criteria for searching for rides.
     */
    interface Criteria {
        val serviceArea: Area
        val timeRange: ClosedRange<Instant>
        val distanceRange: ClosedRange<Length>
        val priceRange: ClosedRange<Currency>
        val rateRange: ClosedRange<Currency>
    }

    data class Draft(
        override val timeToStart: Instant,
        override val plannedRoute: Route,
    ): Ride {
        override val timePosted: Instant?
            get() = null
        override val timeAccepted: Instant?
            get() = null
        override val timeEnded: Instant?
            get() = null
        override val endReason: EndReason?
            get() = null
        override val timeArrived: Instant?
            get() = null
        override val timeStarted: Instant?
            get() = null
        override val actualRoute: Route?
            get() = null
        override val driver: DriverProfile?
            get() = null
        override val rider: RiderProfile?
            get() = null
    }

    data class Immediate(
        override val rider: RiderProfile?,
        override val plannedRoute: Route,
        override val timePosted: Instant,
    ): Ride {
        override val actualRoute: Route?
            get() = null
        override val timeAccepted: Instant?
            get() = null
        override val timeEnded: Instant?
            get() = null
        override val endReason: EndReason?
            get() = null
        override val timeArrived: Instant?
            get() = null
        override val timeToStart: Instant?
            get() = null
        override val timeStarted: Instant?
            get() = null
        override val driver: DriverProfile?
            get() = null
    }

    data class Scheduled(
        override val rider: RiderProfile,
        override val plannedRoute: Route,
        override val timeToStart: Instant,
        override val timePosted: Instant,
    ): Ride {
        override val timeAccepted: Instant?
            get() = null
        override val timeEnded: Instant?
            get() = null
        override val endReason: EndReason?
            get() = null
        override val timeArrived: Instant?
            get() = null
        override val timeStarted: Instant?
            get() = null
        override val actualRoute: Route?
            get() = null
        override val driver: DriverProfile?
            get() = null
    }

    data class Accepted(
        override val rider: RiderProfile,
        override val driver: DriverProfile,
        override val plannedRoute: Route,
        override val timeToStart: Instant,
        override val timePosted: Instant,
        override val timeAccepted: Instant,
    ): Ride {
        override val timeEnded: Instant?
            get() = null
        override val endReason: EndReason?
            get() = null
        override val timeArrived: Instant?
            get() = null
        override val timeStarted: Instant?
            get() = null
        override val actualRoute: Route?
            get() = null
    }

    data class Ended(
        override val rider: RiderProfile,
        override val driver: DriverProfile,
        override val plannedRoute: Route,
        override val timeToStart: Instant,
        override val timePosted: Instant,
        override val timeAccepted: Instant,
        override val timeArrived: Instant,
        override val timeStarted: Instant,
        override val timeEnded: Instant,
        override val actualRoute: Route,
        override val endReason: EndReason,
    ): Ride
}
