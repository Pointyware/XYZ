/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.drive.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import org.pointyware.xyz.drive.viewmodels.DriverSettingsViewModel

/**
 *
 */
@Composable
fun DriverSettingsScreen(
    viewModel: DriverSettingsViewModel
) {
    val state by viewModel.state.collectAsState()

    Column {
        TextField(
            modifier = Modifier.semantics { contentDescription = "Cost of Maintenance" },
            label = { Text("Cost of Maintenance") },
            value = state.maintenanceCost,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = viewModel::onUpdateCostOfMaintenance,
        )
        TextField(
            modifier = Modifier.semantics { contentDescription = "Pickup Rate" },
            label = { Text("Price per Mile (Pickup)") },
            value = state.pickupCost,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = viewModel::onUpdatePickupPricePerMile,
        )
        TextField(
            modifier = Modifier.semantics { contentDescription = "Dropoff Rate" },
            label = { Text("Price per Mile (Dropoff)") },
            value = state.dropoffCost,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = viewModel::onUpdateDropoffPricePerMile,
        )
        Row {
            Button(
                onClick = viewModel::onRevertChanges
            ) {
                Text("Revert")
            }
            Button(
                onClick = viewModel::onSubmitChanges
            ) {
                Text("Save")
            }
        }
    }
}
