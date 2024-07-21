/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.pointyware.xyz.core.ui.BriefProfileCollectionItem
import org.pointyware.xyz.core.viewmodels.CompanyProfileUiState

/**
 * Displays a company profile:
 * - banner
 * - logo
 * - name
 * - tagline
 * - description
 * - phone number
 * - drivers list
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CompanyProfileView(
    state: CompanyProfileUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = state.banner.value,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Banner Image"
            )
            AsyncImage(
                model = state.logo.value,
                contentDescription = "Logo Image",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
        Text(text = state.name)
        Text(text = state.tagline)
        Text(text = state.description)
        Text(text = state.phoneNumber.sequence)
        FlowRow {
            state.drivers.forEach { driver ->
                BriefProfileCollectionItem(
                    state = driver,
                )
            }
        }
    }
}
