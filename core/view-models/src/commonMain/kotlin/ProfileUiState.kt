/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels

import org.pointyware.xyz.core.entities.ride.Accommodation
import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.entities.profile.Gender
import org.pointyware.xyz.core.entities.profile.Name
import org.pointyware.xyz.core.entities.profile.PhoneNumber
import org.pointyware.xyz.core.entities.ride.Rating
import org.pointyware.xyz.core.entities.data.Uri
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

data class RiderProfileUiStateImpl(
    val profileUiState: ProfileUiState,
    override val preferences: String,
    override val disabilities: Set<Disability>,
): RiderProfileUiState, ProfileUiState by profileUiState

val emptyRiderProfile = RiderProfileUiStateImpl(EmptyProfileUiState, "", emptySet())

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
data class CompanyProfileUiStateImpl(
    override val id: Uuid,
    override val banner: Uri,
    override val logo: Uri,
    override val name: String,
    override val tagline: String,
    override val description: String,
    override val phoneNumber: PhoneNumber,
    override val drivers: List<BriefProfileUiState>,
): CompanyProfileUiState
val emptyCompanyProfile = CompanyProfileUiStateImpl(Uuid.nil(), Uri(""), Uri(""), "", "", "", PhoneNumber(""), emptyList())

interface DriverProfileUiState: ProfileUiState {
    val accommodations: Set<Accommodation>
    val company: CompanyProfileUiState
}
data class DriverProfileUiStateImpl(
    val profileUiState: ProfileUiState,
    override val accommodations: Set<Accommodation>,
    override val company: CompanyProfileUiState,
): DriverProfileUiState, ProfileUiState by profileUiState
val emptyDriverProfile = DriverProfileUiStateImpl(EmptyProfileUiState, emptySet(), emptyCompanyProfile)
