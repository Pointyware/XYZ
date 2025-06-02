/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.viewmodels

import org.pointyware.xyz.core.entities.business.Currency
import org.pointyware.xyz.core.entities.geo.Location
import org.pointyware.xyz.core.entities.geo.Route
import org.pointyware.xyz.core.entities.profile.DriverProfile
import org.pointyware.xyz.feature.ride.ui.PaymentSelectionViewState

/**
 * Represents the state of a rider's UI.
 */
sealed interface PassengerDashboardUiState {

    /**
     * The rider is not currently requesting a ride.
     */
    data object Idle: PassengerDashboardUiState

    /**
     * The rider is searching for a destination.
     */
    data class Search(
        val query: String = "",
        val suggestions: List<Location>,
        val paymentSelection: PaymentSelectionViewState
    ): PassengerDashboardUiState

    /**
     * The rider is confirming the ride details.
     */
    data class Confirm(
        val origin: Location,
        val destination: Location,
        val route: Route?,
        val price: Currency?
    ): PassengerDashboardUiState

    /**
     * The rider has posted the ride and is waiting for a driver.
     */
    data class Posted(
        val route: Route,
        val price: Currency
    ): PassengerDashboardUiState

    /**
     * The request has been accepted and the rider is waiting for the driver to arrive.
     */
    data class Waiting(
        /**
         * The driver's information.
         */
        val driver: DriverProfile,
        /**
         * Driver's estimated time of arrival in minutes.
         */
        val eta: Int,
        val route: Route,
    ): PassengerDashboardUiState

    /**
     * The rider is in the car and the ride is in progress.
     */
    data class Riding(
        val driver: DriverProfile,
        val route: Route,
        val eta: Int
    ): PassengerDashboardUiState

    /**
     * The rider has arrived at the destination.
     */
    data class Arrived(
        val driver: DriverProfile,
        val route: Route
    ): PassengerDashboardUiState
}
