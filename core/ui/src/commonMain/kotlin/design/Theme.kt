/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.design

/**
 * Combines primitives to create a XYZ theme
 */

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import org.pointyware.xyz.core.ui.di.UiDependencies

val LocalDateFormatter = compositionLocalOf<DateFormatter> { throw IllegalStateException("DateFormat not provided") }

val LocalResources = compositionLocalOf<Resources> { throw IllegalStateException("UiResources not provided") }

val LocalDimensions = compositionLocalOf<Dimensions> { throw IllegalStateException("LocalDimensions not provided") }

/**
 * Provides access to extended theme values provided at the call site's position in the hierarchy.
 */
object XyzTheme {
    val dateFormatter: DateFormatter
        @Composable
        @ReadOnlyComposable
        get() = LocalDateFormatter.current
    val resources: Resources
        @Composable
        @ReadOnlyComposable
        get() = LocalResources.current
    val dimensions: Dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current
}

/**
 * Extends the Material3 Theme with a [DateFormatter], [Resources], and [Dimensions].
 */
@Composable
fun XyzTheme(
    uiDependencies: UiDependencies,
    isDark: Boolean = false,
    content: @Composable ()->Unit,
) {
    CompositionLocalProvider(
        LocalDateFormatter provides SimpleDateFormatter,
        LocalResources provides uiDependencies.resources,
        LocalDimensions provides MultiplatformDimensions,
    ) {
        MaterialTheme(
            colorScheme = if (isDark) darkColors else lightColors,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }
}

@Preview
@Composable
fun XyzThemePreview() {
    XyzTheme(
        uiDependencies = EmptyTestUiDependencies(),
    ) {
        Surface {
            Text("ooh, a button")
            Button(onClick = { println("Click") }) {
                Text("Click me!")
            }
        }
    }
}
