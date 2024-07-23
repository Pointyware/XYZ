/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels.drive

import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.entities.data.Uri

/**
 * Contains just enough information to represent a company in a list.
 */
data class BriefCompanyProfileUiState(
    val id: Uuid,
    val logo: Uri,
    val name: String
)
