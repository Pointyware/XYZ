/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.data

import org.pointyware.xyz.core.entities.geo.LatLong
import org.pointyware.xyz.drive.RideFilter
import org.pointyware.xyz.drive.entities.DriverRates

/**
 * Maintains Driver settings.
 */
interface DriverSettingsRepository {
    /**
     *
     */
    fun getFilter(): RideFilter
    /**
     *
     */
    fun setFilter(filter: RideFilter)
    /**
     *
     */
    fun getDriverRates(): DriverRates
    /**
     *
     */
    fun setDriverRates(rates: DriverRates)

    fun getDriverLocation(): LatLong
    fun setDriverLocation(location: LatLong)
}
