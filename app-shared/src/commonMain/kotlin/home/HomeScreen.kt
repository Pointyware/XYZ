package org.pointyware.xyz.shared.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class HomeScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
)

/**
 * Main view shown when there is no active navigation.
 */
@Composable
fun HomeScreen(
    state: HomeScreenState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(state = rememberScrollState())
    ) {

    }
}
