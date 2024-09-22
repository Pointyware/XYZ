/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package viewmodels

import org.pointyware.xyz.core.entities.ride.PendingRide
import org.pointyware.xyz.drive.viewmodels.RideRequestUiState

/**
 * Represents the ui state of the provider dashboard.
 */
sealed interface ProviderDashboardUiState {
    /**
     * The driver is available to accept requests is waiting for a new request.
     */
    data class AvailableRequests(
        val requests: List<RideRequestUiState>
    ): ProviderDashboardUiState

    /**
     * The driver has accepted a request and is on their way to pick up the passenger.
     */
    data class Accepted(
        val ride: PendingRide,
        val atOrigin: Boolean,
    ): ProviderDashboardUiState

    /**
     * The rider has canceled the request before the driver arrived.
     */
    data object RiderCanceled : ProviderDashboardUiState

    /**
     * The driver has picked up the rider and is on their way to the destination.
     */
    data object InProgress : ProviderDashboardUiState

    /**
     * The rider has canceled the request after the driver arrived.
     */
    data object RiderCanceledLate : ProviderDashboardUiState

    /**
     * The driver has canceled the request.
     */
    data object DriverCanceled : ProviderDashboardUiState

    /**
     * The ride has been completed.
     */
    data object Completed : ProviderDashboardUiState
}
