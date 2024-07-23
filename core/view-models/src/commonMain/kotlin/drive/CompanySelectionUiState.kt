/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.viewmodels.drive

/**
 * Supports associating a company with a new driver.
 */
data class CompanySelectionUiState(
    val search: String,
    val suggestions: List<BriefCompanyProfileUiState>,
    val selected: BriefCompanyProfileUiState? = null,
) {
    companion object {
        val empty = CompanySelectionUiState(
            search = "",
            suggestions = emptyList()
        )
    }
}