/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.ride

import org.pointyware.xyz.core.entities.geo.LatLong

/**
 * Associates useful information with [LatLong] coordinates.
 *
 * Could be a street address or a point along some roadway with no specific information.
 */
interface Location {
    val coordinates: LatLong

    val name: String
    val address: String
    val city: String
    val state: String
    val zip: String
    val country: String
}

private data class SimpleLocation(
    override val coordinates: LatLong,
    override val name: String,
    override val address: String,
    override val city: String,
    override val state: String,
    override val zip: String,
    override val country: String,
): Location


fun Location(
    lat: Double,
    long: Double,
    name: String,
    address: String = "1804 S Perkins Rd",
    city: String = "Stillwater",
    state: String = "Ok",
    zip: String = "74074",
    country: String = "United States",
): Location = SimpleLocation(
    LatLong(lat, long),
    name,
    address,
    city,
    state,
    zip,
    country,
)
