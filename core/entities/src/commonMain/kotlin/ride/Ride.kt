/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.geo.Length
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.core.entities.profile.RiderProfile
import kotlin.time.Duration

/**
 * Describes a ride in one of many possible states.
 */
sealed interface Ride {

    /**
     * The unique identifier of the ride.
     */
    val id: Uuid

    /**
     * The current status of the ride as it progresses through the system.
     */
    val status: Status

    /**
     * The time the ride was posted to the system by the rider.
     */
    val timePosted: Instant?

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
     *
     */
    sealed interface Status {
        /**
         * Marker interface for route progress.
         */
        sealed interface RouteProgress {
            /**
             * The ride route has no progress.
             */
            interface Unrealized: RouteProgress

            /**
             * A ride with some or all of the route completed.
             */
            interface Realized: RouteProgress {
                val actualRoute: Route
            }
        }

        /**
         * The ride is posted and waiting for a driver to accept it.
         * Possible transitions are [Active], []
         */
        data object Immediate : Status, RouteProgress.Unrealized

        /**
         * The ride is scheduled for a future time and waiting for a driver to accept it.
         * Possible transitions are [Accepted], [Ended]
         */
        data class Scheduled(
            val timeToStart: Instant
        ): Status, RouteProgress.Unrealized

        /**
         * The ride has been accepted by a driver for future completion.
         * Possible transitions are [Active], [Ended]
         */
        data class Accepted(
            val timeToStart: Instant
        ): Status, RouteProgress.Unrealized

        /**
         * The ride is in progress.
         * Possible transitions are [Ended]
         */
        data class Active(
            override val actualRoute: Route
        ): Status, RouteProgress.Realized

        /**
         * The ride has ended, either by completion or cancellation.
         */
        abstract class Ended(
            val endReason: EndReason
        ) : Status, RouteProgress

        /**
         * The driver canceled the ride before it started.
         */
        data object DriverCanceled : Ended(EndReason.DriverCanceled), RouteProgress.Unrealized

        /**
         * The rider canceled the ride before it started.
         */
        data object RiderCanceled : Ended(EndReason.RiderCanceled), RouteProgress.Unrealized

        /**
         * The ride was completed successfully.
         */
        data class Completed(
            override val actualRoute: Route
        ) : Ended(EndReason.Completed), RouteProgress.Realized

        /**
         * The rider canceled the ride after it started.
         */
        data class RiderCanceledLate(
            override val actualRoute: Route
        ) : Ended(EndReason.RiderCanceledLate), RouteProgress.Realized

        /**
         * The driver canceled the ride after it started.
         */
        data class DriverCanceledLate(
            override val actualRoute: Route
        ) : Ended(EndReason.RiderCanceledLate), RouteProgress.Realized
    }
}
