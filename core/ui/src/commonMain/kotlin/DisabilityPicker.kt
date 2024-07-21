/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.pointyware.xyz.core.entities.Disability

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
    onAdd: (Disability)->Unit, // or? onUpdateSelected: (List<Disability>)->Unit
    onRemove: (Disability)->Unit
) {
    Column(
        modifier = modifier
    ) {

        FlowRow {
            state.selected.forEach {
                DisabilityChip(
                    value = it,
                    onRemove = { onRemove(it) }
                )
            }
        }
        var showDialog by remember { mutableStateOf(false) }
        Button(onClick = { showDialog = true }) {
            Text("Add Disability")
        }
        if (showDialog) {
            DisabilityPickerDialog(
                onDismiss = { showDialog = false },
                onSelectDisability = {
                    onAdd(it)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun DisabilityPickerDialog(
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

            Button(onClick = { onSelectDisability(Disability.Blind) }) {
                Text("Blind")
            }
            Button(onClick = { onSelectDisability(Disability.Deaf) }) {
                Text("Deaf")
            }
            Button(onClick = { onSelectDisability(Disability.Mobility) }) {
                Text("Mobility")
            }
            Button(onClick = { onSelectDisability(Disability.ServiceAnimal) }) {
                Text("Service Animal")
            }
            Button(onClick = { onSelectDisability(Disability.Allergy) }) {
                Text("Allergy")
            }
            // TODO: replace with more nuanced entities
        }
    }
}
