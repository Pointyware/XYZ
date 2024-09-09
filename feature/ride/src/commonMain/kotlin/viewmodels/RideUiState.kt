/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.viewmodels

import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.ride.Location
import org.pointyware.xyz.core.entities.ride.Route
import org.pointyware.xyz.core.viewmodels.BriefProfileUiState

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
        val origin: Location,
        val destination: Location,
        val route: Route?,
        val price: Currency?
    ): RideUiState

    /**
     * The rider has posted the ride and is waiting for a driver.
     */
    data class Posted(
        val route: Route,
        val price: Currency
    ): RideUiState

    /**
     * The request has been accepted and the rider is waiting for the driver to arrive.
     */
    data class Waiting(
        /**
         * The driver's name.
         */
        val driver: BriefProfileUiState,
        /**
         * Driver's estimated time of arrival in minutes.
         */
        val eta: Int,
        val route: Route,
    ): RideUiState

    /**
     * The rider is in the car and the ride is in progress.
     */
    data class Riding(
        val driver: BriefProfileUiState,
        val route: Route,
        val eta: Int
    ): RideUiState
}
