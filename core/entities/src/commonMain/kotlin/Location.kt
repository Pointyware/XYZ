/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

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
