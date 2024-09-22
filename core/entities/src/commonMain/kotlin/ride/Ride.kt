/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import kotlinx.datetime.Instant
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.geo.Route
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

data class SimpleRide(
    override val id: Uuid,
    override val status: Ride.Status,
    override val timePosted: Instant? = null,
    override val timeAccepted: Instant? = null,
    override val timeArrived: Instant? = null,
    override val timeStarted: Instant? = null,
    override val timeEnded: Instant? = null,
    override val plannedRoute: Route? = null,
    override val driver: DriverProfile? = null,
    override val rider: RiderProfile? = null
): Ride

fun activeRide(
    id: Uuid,
    rider: RiderProfile,
    plannedRoute: Route,
    timePosted: Instant,
    timeAccepted: Instant
) = SimpleRide(
    id = id,
    status = Ride.Status.Immediate,
    rider = rider,
    plannedRoute = plannedRoute,
    timePosted = timePosted
)

/**
 * Create a new ride that is waiting for a driver to accept it.
 */
fun planRide(
    id: Uuid,
    rider: RiderProfile,
    plannedRoute: Route,
    timePosted: Instant
) = PlannedRide(
    id = id,
    rider = rider,
    plannedRoute = plannedRoute,
    timePosted = timePosted
)

/**
 * A ride that has been planned by a rider and is waiting for a driver to accept it.
 */
data class PlannedRide(
    override val id: Uuid,
    override val rider: RiderProfile,
    override val plannedRoute: Route,
    override val timePosted: Instant,
    override val driver: DriverProfile? = null,
    override val timeAccepted: Instant? = null,
    override val timeArrived: Instant? = null,
    override val timeStarted: Instant? = null,
    override val timeEnded: Instant? = null,
): Ride {
    override val status: Ride.Status
        get() = Ride.Status.Immediate

    fun accept(driver: DriverProfile, timeAccepted: Instant): PendingRide {
        return PendingRide(
            id = id,
            rider = rider,
            plannedRoute = plannedRoute,
            timePosted = timePosted,
            driver = driver,
            timeAccepted = timeAccepted
        )
    }
}

data class PendingRide(
    override val id: Uuid,
    override val rider: RiderProfile,
    override val plannedRoute: Route,
    override val timePosted: Instant,
    override val driver: DriverProfile,
    override val timeAccepted: Instant,
    override val timeArrived: Instant? = null,
    override val timeStarted: Instant? = null,
    override val timeEnded: Instant? = null,
): Ride {

    override val status: Ride.Status
        get() = Ride.Status.Immediate

    fun arrive(timeArrived: Instant): ActiveRide {
        return ActiveRide(
            id = id,
            rider = rider,
            plannedRoute = plannedRoute,
            timePosted = timePosted,
            driver = driver,
            timeAccepted = timeAccepted,
            timeArrived = timeArrived
        )
    }
}

data class ActiveRide(
    override val id: Uuid,
    override val rider: RiderProfile,
    override val plannedRoute: Route,
    override val timePosted: Instant,
    override val driver: DriverProfile,
    override val timeAccepted: Instant,
    override val timeArrived: Instant,
    override val timeStarted: Instant? = null,
    override val timeEnded: Instant? = null,
): Ride {

    override val status: Ride.Status
        get() = Ride.Status.Active(TODO())

    fun start(timeStarted: Instant): CompletingRide {
        return CompletingRide(
            id = id,
            rider = rider,
            plannedRoute = plannedRoute,
            timePosted = timePosted,
            driver = driver,
            timeAccepted = timeAccepted,
            timeArrived = timeArrived,
            timeStarted = timeStarted
        )
    }
}

data class CompletingRide(
    override val id: Uuid,
    override val rider: RiderProfile,
    override val plannedRoute: Route,
    override val timePosted: Instant,
    override val driver: DriverProfile,
    override val timeAccepted: Instant,
    override val timeArrived: Instant,
    override val timeStarted: Instant,
    override val timeEnded: Instant? = null,
): Ride {

    override val status: Ride.Status
        get() = Ride.Status.Active(TODO())

    fun complete(timeEnded: Instant): CompletedRide {
        return CompletedRide(
            id = id,
            rider = rider,
            plannedRoute = plannedRoute,
            timePosted = timePosted,
            driver = driver,
            timeAccepted = timeAccepted,
            timeArrived = timeArrived,
            timeStarted = timeStarted,
            timeEnded = timeEnded
        )
    }
}

data class CompletedRide(
    override val id: Uuid,
    override val rider: RiderProfile,
    override val plannedRoute: Route,
    override val timePosted: Instant,
    override val driver: DriverProfile,
    override val timeAccepted: Instant,
    override val timeArrived: Instant,
    override val timeStarted: Instant,
    override val timeEnded: Instant,
): Ride {
    override val status: Ride.Status
        get() = Ride.Status.Completed(TODO())
}
