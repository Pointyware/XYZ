/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

/**
 * A composable that provides a location-based navigation root.
 */
@Composable
fun <K, V> LocationRoot(
    navController: StackNavigationController<K, V>,

    modifier: Modifier = Modifier,
    content: @Composable LocationRootScope<K, V>.() -> Unit,
) {
    // TODO: remove navigation callbacks in "routing" functions when type-safe navigation is implemented
    val locationRootScope = LocationRootScopeImpl<K, V>()
    locationRootScope.content()

    val currentLocation by navController.currentLocation.collectAsState()
    Box(modifier = modifier) {
        val currentContent = locationRootScope.locations[currentLocation]
        // TODO: replace with "routing" function when type-safe navigation is implemented; location(route(...)) allows variables in paths, while navigating to a route requires matching variables (or defaults) in the route
        currentContent?.invoke(navController) ?: throw IllegalArgumentException("No content for location $currentLocation")
    }
}

interface LocationRootScope<K, V> {
    @Composable
    fun location(key: K, content: @Composable (StackNavigationController<K, V>) -> Unit)
}

private class LocationRootScopeImpl<K, V> : LocationRootScope<K, V> {

    val locations = mutableMapOf<K, @Composable (StackNavigationController<K, V>) -> Unit>()
    @Composable
    override fun location(key: K, content: @Composable (StackNavigationController<K, V>) -> Unit) {
        locations[key] = content
    }
}
