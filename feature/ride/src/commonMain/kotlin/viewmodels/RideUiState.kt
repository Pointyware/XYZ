/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.viewmodels

import org.pointyware.xyz.core.entities.ride.Location

/**
 * Represents the state of a rider's UI.
 */
sealed interface RideUiState {

    /**
     * The rider is not currently requesting a ride.
     */
    data object Idle: RideUiState

    /**
     * The rider is searching for a destination.
     */
    data class Search(
        val query: String = "",
        val suggestions: List<Location>
    ): RideUiState

    /**
     * The rider is confirming the ride details.
     */
    data class Confirm(
        val destination: String,
        val price: Double
    ): RideUiState

    /**
     * The rider has posted the ride and is waiting for a driver.
     */
    data class Posted(
        val destination: String,
        val price: Double
    ): RideUiState

    /**
     * The request has been accepted and the rider is waiting for the driver to arrive.
     */
    data class Waiting(
        /**
         * The driver's name. TODO: replace with BriefProfile?
         */
        val driver: String,
        /**
         * Driver's estimated time of arrival in minutes.
         */
        val eta: Int
    ): RideUiState

    /**
     * The rider is in the car and the ride is in progress.
     */
    data class Riding(
        val driver: String,
        val eta: Int
    ): RideUiState
}
