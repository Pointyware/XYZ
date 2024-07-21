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

interface RiderProfileUiState: ProfileUiState {
    val preferences: String
    val disabilities: Set<Disability>
}


interface CompanyProfileUiState {
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
