/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.profile

import kotlinx.serialization.Serializable
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.business.Company
import org.pointyware.xyz.core.entities.ride.Accommodation

/**
 * Externally-facing profile data.
 */
interface Profile {
    val id: Uuid
    val name: Name
    val gender: Gender
    val picture: Uri
}

/**
 * Profile data for a driver.
 */
@Serializable
class DriverProfile(
    override val id: Uuid,
    override val name: Name,
    override val gender: Gender,
    override val picture: Uri,
    val accommodations: Set<Accommodation>,
    val company: Company
): Profile

/**
 * Profile data for a rider.
 */
@Serializable
class RiderProfile(
    override val id: Uuid,
    override val name: Name,
    override val gender: Gender,
    override val picture: Uri,
    val disabilities: Set<Disability>,
    val preferences: String
): Profile
