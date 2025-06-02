/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.data

import org.pointyware.xyz.core.entities.data.Uri
import org.pointyware.xyz.core.entities.profile.PhoneNumber

/**
 * Separate from [org.pointyware.xyz.core.entities.business.Company] entity to allow representation without [org.pointyware.xyz.core.entities.profile.DriverProfile] details.
 */
data class CompanyRecord(
    val name: String,
    val tagline: String,
    val bio: String,
    val banner: Uri,
    val logo: Uri,
    val phone: PhoneNumber,
)
