/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.pointyware.xyz.core.entities.profile.Disability
import org.pointyware.xyz.core.ui.design.XyzTheme

data class DisabilityPickerState(
    val selected: List<Disability>
)

/**
 * Displays the current list of disabilities and allows the user to select more or remove some.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DisabilityPicker(
    state: DisabilityPickerState,
    modifier: Modifier = Modifier,
    onUpdateSelected: (List<Disability>)->Unit,
) {
    Column(
        modifier = modifier
    ) {

        FlowRow {
            state.selected.forEach {
                DisabilityChip(
                    value = it,
                    modifier = Modifier.padding(XyzTheme.dimensions.paddingSmall),
                    onRemove = { onUpdateSelected(state.selected - it) }
                )
            }
        }
        var showDialog by remember { mutableStateOf(false) }
        Button(onClick = { showDialog = true }) {
            Text("Add Disability")
        }
        if (showDialog) {
            val options = listOf(
                Disability.Deaf, Disability.Blind,
                Disability.Mobility, Disability.Allergy,
                Disability.ServiceAnimal
            )
            DisabilityPickerDialog(
                options = options - state.selected.toSet(),
                onDismiss = { showDialog = false },
                onSelectDisability = {
                    onUpdateSelected(state.selected + it)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun DisabilityPickerDialog(
    options: List<Disability>,
    modifier: Modifier = Modifier,
    onSelectDisability: (Disability)->Unit,
    onDismiss: ()->Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier
        ) {
            Text("Disability Picker")

            options.forEach {
                Button(onClick = { onSelectDisability(it) }) {
                    Text(it.toString())
                }
            }
        }
    }
}
