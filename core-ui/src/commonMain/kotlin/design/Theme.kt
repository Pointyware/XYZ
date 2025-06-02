/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
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

val LocalDateFormatter = compositionLocalOf<DateFormatter> { throw IllegalStateException("DateFormat not provided") }

val LocalDimensions = compositionLocalOf<Dimensions> { throw IllegalStateException("LocalDimensions not provided") }

/**
 * Provides access to extended theme values provided at the call site's position in the hierarchy.
 */
object XyzTheme {
    val dateFormatter: DateFormatter
        @Composable
        @ReadOnlyComposable
        get() = LocalDateFormatter.current
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
    isDark: Boolean = false,
    content: @Composable ()->Unit,
) {
    CompositionLocalProvider(
        LocalDateFormatter provides SimpleDateFormatter,
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
    XyzTheme {
        Surface {
            Text("ooh, a button")
            Button(onClick = { println("Click") }) {
                Text("Click me!")
            }
        }
    }
}
