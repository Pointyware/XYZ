/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.data

import org.pointyware.xyz.core.entities.business.Rate.Companion.div
import org.pointyware.xyz.core.entities.business.dollarCents
import org.pointyware.xyz.core.entities.geo.LatLong
import org.pointyware.xyz.core.entities.geo.LengthUnit
import org.pointyware.xyz.drive.RideFilter
import org.pointyware.xyz.drive.SimpleRideFilter
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

class DriverSettingsRepositoryImpl(

): DriverSettingsRepository {
    override fun getFilter(): RideFilter {
        TODO("Not yet implemented")
    }

    override fun setFilter(filter: RideFilter) {
        TODO("Not yet implemented")
    }

    override fun getDriverRates(): DriverRates {
        TODO("Not yet implemented")
    }

    override fun setDriverRates(rates: DriverRates) {
        TODO("Not yet implemented")
    }

    override fun getDriverLocation(): LatLong {
        TODO("Not yet implemented")
    }

    override fun setDriverLocation(location: LatLong) {
        TODO("Not yet implemented")
    }
}

class TestDriverSettingsRepository(

): DriverSettingsRepository {

    private var filter: RideFilter = SimpleRideFilter.Permissive
    override fun getFilter(): RideFilter {
        return filter
    }

    override fun setFilter(filter: RideFilter) {
        this.filter = filter
    }

    private var rates: DriverRates = DriverRates(
        maintenanceCost = 0L.dollarCents() / LengthUnit.MILES,
        pickupCost = 0L.dollarCents() / LengthUnit.MILES,
        dropoffCost = 0L.dollarCents() / LengthUnit.MILES
    )
    override fun getDriverRates(): DriverRates {
        return rates
    }

    override fun setDriverRates(rates: DriverRates) {
        this.rates = rates
    }

    private var location: LatLong = LatLong(0.0, 0.0)
    override fun getDriverLocation(): LatLong {
        return location
    }

    override fun setDriverLocation(location: LatLong) {
        this.location = location
    }
}
