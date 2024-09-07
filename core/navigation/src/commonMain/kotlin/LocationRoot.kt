/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.navigation

import androidx.compose.animation.AnimatedContent
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
    val locationRootScope = LocationRootScopeImpl<K, V>()
    locationRootScope.content()

    val currentLocation by navController.currentLocation.collectAsState()
    AnimatedContent(
        targetState = currentLocation,
        modifier = modifier,
    ) {
        val currentContent = locationRootScope.locations[currentLocation]
        currentContent?.invoke(navController) ?: throw IllegalArgumentException("No content for location $currentLocation")
    }
}

interface LocationRootScope<K, V> {
    @Composable
    fun location(key: K, content: @Composable (StackNavigationController<K, V>) -> Unit)
}

private class LocationRootScopeImpl<K, V> : LocationRootScope<K, V> {

    // TODO: replace with ComposeLambda
    val locations = mutableMapOf<K, @Composable (StackNavigationController<K, V>) -> Unit>()
    @Composable
    override fun location(key: K, content: @Composable (StackNavigationController<K, V>) -> Unit) {
        locations[key] = content
    }
}
