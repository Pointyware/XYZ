/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.desktop

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import org.pointyware.xyz.desktop.di.desktopModule
import org.pointyware.xyz.shared.XyzApp
import org.pointyware.xyz.shared.di.setupKoin
import org.pointyware.xyz.ui.Res
import org.pointyware.xyz.ui.tray_icon

/**
 * Main entry point for the desktop application.
 */
fun main() {

    // Configure Koin Dependency Injection (actually Service Locator)
    setupKoin(platformModule = desktopModule())

    // Get the dependencies
    application(exitProcessOnExit = false) {

        val state = rememberWindowState()
        // TODO: observe window state changes for configuration scope

        Window(
            title = "My Application",
            state = state,
            onCloseRequest = ::exitApplication
        ) {
            XyzApp(
                isDarkTheme = isSystemInDarkTheme()
            )
        }

        Tray(
            icon = painterResource(Res.drawable.tray_icon),
            menu = {
                Menu("File") {
                }
            }
        )
    }
}
