/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities

/**
 *
 */
interface Profile {
    val email: String
    val name: Name
    val phone: PhoneNumber
    val gender: Gender
    val picture: Uri
}

class DriverProfile(
    override val email: String,
    override val name: Name,
    override val phone: PhoneNumber,
    override val gender: Gender,
    override val picture: Uri,
    val accommodations: Set<Accommodation>,
    val company: Company
): Profile

class RiderProfile(
    override val email: String,
    override val name: Name,
    override val phone: PhoneNumber,
    override val gender: Gender,
    override val picture: Uri,
    val disabilities: Set<Disability>,
    val preferences: String
): Profile
