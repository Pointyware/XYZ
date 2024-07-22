/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.Uuid
import org.pointyware.xyz.core.viewmodels.drive.CompanyProfileUiState

/**
 * A search bar that allows the user to search for a company that shows up in a list or register a new company.
 */
@Composable
fun CompanyPicker(
    value: CompanyProfileUiState,
    modifier: Modifier = Modifier,
    onSelectCompany: (Uuid) -> Unit
) {
    TODO("Not yet Implemented" +
            "")
}
