/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.Accommodation
import org.pointyware.xyz.core.entities.Disability
import org.pointyware.xyz.core.entities.Gender
import org.pointyware.xyz.core.entities.Name
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
    val name: String
}

interface DriverProfileUiState: ProfileUiState {
    val accommodations: Set<Accommodation>
    val company: CompanyProfileUiState
}
