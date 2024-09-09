/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.viewmodels

/**
 *
 * - Idle: The rider is not currently requesting a ride.
 * - Search: The rider is searching for a destination.
 * - Confirm: The rider is confirming the ride details.
 * - Posted: The rider has posted the ride and is waiting for a driver.
 * - Waiting: The request has been accepted and the rider is waiting for the driver to arrive.
 * - Riding: The rider is in the car and the ride is in progress.
 */
sealed interface RideUiState {

    /**
     * The rider is not currently requesting a ride.
     */
    data object Idle: RideUiState

    /**
     *
     */
    data class Search(
        val query: String = "",
        val suggestions: List<String>
    ): RideUiState

    data class Confirm(
        val destination: String,
        val price: Double
    ): RideUiState

    data class Posted(
        val destination: String,
        val price: Double
    ): RideUiState

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

    data class Riding(
        val driver: String,
        val eta: Int
    ): RideUiState
}
