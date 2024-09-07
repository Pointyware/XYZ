/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.pointyware.xyz.core.entities.data.Uri

/**
 * Previews an image (if one is selected) and allows the user to select a new image.
 */
@Composable
fun ImagePicker(
    onImageSelected: (Uri) -> Unit,
    placeholder: Uri? = null,
    modifier: Modifier = Modifier,
    emptyContent: @Composable ()->Unit = { Text("Click to select an image") },
    previewContent: @Composable (Uri) -> Unit,
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.clickable { isDialogOpen = true }
    ) {
        var imageUri by remember { mutableStateOf(placeholder) }
        if (isDialogOpen) {
            ImagePickerDialog(
                onConfirm = {
                    val newUri = Uri(it)
                    imageUri = newUri
                    onImageSelected(newUri)
                },
                onDismiss = {
                    imageUri = null
                }
            )
        }
        val imageCapture = imageUri
        if (imageCapture == null) {
            emptyContent()
        } else {
            previewContent(imageCapture)
        }
    }
}

@Composable
fun ImagePickerDialog(
    modifier: Modifier = Modifier,
    onConfirm: (String)->Unit,
    onDismiss: ()->Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier,
        ) {
            var imageUri by remember { mutableStateOf("") }
            TextField(
                value = imageUri,
                onValueChange = { imageUri = it },
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        onConfirm(imageUri)
                    }
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}
