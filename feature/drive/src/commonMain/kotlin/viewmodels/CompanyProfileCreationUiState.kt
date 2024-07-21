/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.viewmodels

import org.pointyware.xyz.core.entities.PhoneNumber
import org.pointyware.xyz.core.entities.Uri
import org.pointyware.xyz.core.viewmodels.BriefProfileUiState

/**
 *
 */
data class CompanyProfileCreationUiState(
    val bannerUri: Uri,
    val logoUri: Uri,
    val name: String,
    val tagline: String,
    val description: String,
    val phoneNumber: PhoneNumber,
    val drivers: List<BriefProfileUiState>,
)
