/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.Accommodation
import org.pointyware.xyz.core.entities.Disability
import org.pointyware.xyz.core.entities.Gender
import org.pointyware.xyz.core.entities.Name
import org.pointyware.xyz.core.entities.PhoneNumber
import org.pointyware.xyz.core.entities.Rating
import org.pointyware.xyz.core.entities.Uri
import org.pointyware.xyz.core.entities.Uuid

/**
 * A detailed profile UI state. For less detail see [BriefProfileUiState].
 */
interface ProfileUiState {
    val image: Uri
    val fullName: Name
    val gender: Gender
    val age: Int
    val bio: String
    val rating: Rating
}

object EmptyProfileUiState: ProfileUiState {
    override val image: Uri = Uri("")
    override val fullName: Name = Name("", "", "")
    override val gender: Gender = Gender.NotSpecified
    override val age: Int = 0
    override val bio: String = ""
    override val rating: Rating = Rating.FIVE
}

interface RiderProfileUiState: ProfileUiState {
    val preferences: String
    val disabilities: Set<Disability>
}


interface CompanyProfileUiState {
    val id: Uuid
    val banner: Uri
    val logo: Uri
    val name: String
    val tagline: String
    val description: String
    val phoneNumber: PhoneNumber
    val drivers: List<BriefProfileUiState>
}

interface DriverProfileUiState: ProfileUiState {
    val accommodations: Set<Accommodation>
    val company: CompanyProfileUiState
}
