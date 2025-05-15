/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.profile

import kotlinx.serialization.Serializable
import org.pointyware.xyz.core.entities.Name
import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.business.Business
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.ride.Accommodation
import kotlin.uuid.ExperimentalUuidApi

/**
 * Externally-facing profile data.
 */
@OptIn(ExperimentalUuidApi::class)
@Serializable
sealed interface Profile {
    val id: Uuid
    val name: Name
    val gender: Gender
    val picture: Uri
}

/**
 * Profile data for a driver.
 */
@OptIn(ExperimentalUuidApi::class)
@Serializable
class DriverProfile(
    override val id: Uuid,
    override val name: Name,
    override val gender: Gender,
    override val picture: Uri,
    val accommodations: Set<Accommodation>,
    val business: Business
): Profile

/**
 * Profile data for a rider.
 */
@OptIn(ExperimentalUuidApi::class)
@Serializable
class RiderProfile(
    override val id: Uuid,
    override val name: Name,
    override val gender: Gender,
    override val picture: Uri,
    val disabilities: Set<Disability>,
    val preferences: String
): Profile
