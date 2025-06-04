/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.xyz.core.entities.geo.LatLong
import org.pointyware.xyz.core.viewmodels.MapUiState

/**
 * Presents a map that
 */
@Composable
fun MapView(
    state: MapUiState,
    modifier: Modifier = Modifier,
    onTapLocation: (LatLong) -> Unit = {},
) {
    /*
    TODO: Setup Google Maps API
      1. Provision GCloud resources
      2. Setup Google Maps API
      3. Add the Google Maps API key to the AndroidManifest.xml
      4. Add GoogleMap composable
     */
    /*
    TODO: Implement the MapView composable. This composable should display a map with the following features:
      1. A map centered at a given location.
      2. A marker at the user's current location.
      3. A marker at each Point of Interest (POI) in the state.
        1. X at the object location (usually user)
        2. Y at the origin location
        3. Z at the destination location
        4. A (other-color) dot at the driver location
        5. A (user-color) dot at the passenger location
     */
}
