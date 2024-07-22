/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels.drive

import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.PhoneNumber
import org.pointyware.xyz.core.viewmodels.BriefProfileUiState

/**
 *
 */
data class CompanyProfileUiState(
    val id: Uuid,
    val banner: Uri,
    val logo: Uri,
    val name: String,
    val tagline: String,
    val description: String,
    val phoneNumber: PhoneNumber,
    val drivers: List<BriefProfileUiState>,
) {
    companion object {
        val empty = CompanyProfileUiState(
            Uuid.nil(),
            Uri.nullDevice,
            Uri.nullDevice,
            "",
            "",
            "",
            PhoneNumber(""),
            emptyList()
        )
    }
}