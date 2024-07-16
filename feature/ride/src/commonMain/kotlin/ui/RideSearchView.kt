/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class RideSearchViewState(
    val isExpanded: Boolean,
    val query: String,
    val results: List<String>,
)

/**
 * Presents like a Floating-Action-Button when collapsed and a search bar when expanded.
 */
@Composable
fun RideSearchView(
    state: RideSearchViewState,
    modifier: Modifier = Modifier,
    onCollapse: ()->Unit,
    onExpand: ()->Unit,
    onSearch: (String)-> Unit,
) {
    val shape = if (state.isExpanded) {
        // Rounded corners
        RoundedCornerShape(8.dp)
    } else {
        CircleShape
    }
    Surface(
        modifier = modifier.background(color = MaterialTheme.colorScheme.primary, shape = shape)
    ) {
        if (state.isExpanded) {
            Row {
                TextField(
                    value = state.query,
                    onValueChange = { /* TODO: Implement search */ },
                    label = { Text("Search") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Button(onClick = {
                    if (state.query.isNotBlank()) {
                        onSearch(state.query)
                    } else {
                        onCollapse()
                    }
                }) {
                    Text("Search")
                }
            }
        } else {
            Button(onClick = onExpand) {
                Text("New Ride")
            }
        }
    }
}
