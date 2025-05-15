/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels.drive

import kotlin.uuid.Uuid
import org.pointyware.xyz.core.entities.data.Uri
import kotlin.uuid.ExperimentalUuidApi

/**
 * Contains just enough information to represent a company in a list.
 */
@OptIn(ExperimentalUuidApi::class)
data class BriefCompanyProfileUiState(
    val id: Uuid,
    val logo: Uri,
    val name: String
) {
    companion object {
        val independent = BriefCompanyProfileUiState(
            id = Uuid.NIL,
            logo = Uri.nullDevice,
            name = "Independent"
        )
    }
}
