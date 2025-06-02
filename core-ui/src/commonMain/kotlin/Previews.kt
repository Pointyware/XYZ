/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pointyware.xyz.core.ui.Dimension.Companion.by

enum class UiMode {
    Light,
    Dark
}

enum class Orientation {
    Vertical,
    Horizontal
}

data class Dimension(
    val width: Int,
    val height: Int
) {
    val isPortrait: Boolean
        get() = height > width
    val isLandscape: Boolean
        get() = width > height
    val isSquare: Boolean
        get() = height == width

    val flipped: Dimension
        get() = height by width
    companion object {
        infix fun Int.by (other: Int): Dimension = Dimension(this, other)
    }
}

/**
 * Defines popular device configurations for previewing on Desktop.
 */
@Composable
fun PopularDevicePreviews(
    uiModes: List<UiMode> = listOf(UiMode.Light, UiMode.Dark),
    orientations: List<Orientation> = listOf(Orientation.Vertical, Orientation.Horizontal),
    dimensions: List<Dimension> = listOf(
        320 by 480,
        768 by 1024,
        1080 by 1920,
        1440 by 2560
    ),
    content: @Composable () -> Unit,
) {
    Column {
        uiModes.forEach { uiMode ->
            Row {

                dimensions.forEach { dimension ->

                    orientations.forEach { orientation ->

                        DevicePreview(
                            device = DeviceConfig(
                                name = "${dimension.width}x${dimension.height} ${uiMode.name} ${orientation.name}",
                                width = dimension.width,
                                height = dimension.height,
//                                density = 1,
//                                fontScale = 1f,
                                uiMode = uiMode,
                                orientation = orientation,
                            ),
                            content = content
                        )
                    }
                }
            }
        }
    }
}

data class DeviceConfig(
    val name: String,
    val width: Int,
    val height: Int,
//    val density: Int,
//    val fontScale: Float,
    val uiMode: UiMode,
    val orientation: Orientation
)

private const val LEVEL_OFF = 0
private const val LEVEL_INFO = 1
private const val LEVEL_DEBUG = 2
private const val LEVEL_VERBOSE = 3
internal var previewLevel: Int = LEVEL_OFF

@Composable
fun DevicePreview(
    device: DeviceConfig,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.border(1.dp, Color.White)
    ) {
        Text("Device: ${device.name}")
        if (previewLevel >= LEVEL_DEBUG) {
            Text("Width: ${device.width}")
            Text("Height: ${device.height}")
//            Text("Density: ${deviceConfig.density}")
//            Text("Font Scale: ${deviceConfig.fontScale}")
            Text("UI Mode: ${device.uiMode}")
            Text("Orientation: ${device.orientation}")
        }

        Box(
            modifier = Modifier
                .requiredSize(device.width.dp, device.height.dp)
                .border(1.dp, Color.Black)
        ) {
            content()
        }
    }
}
