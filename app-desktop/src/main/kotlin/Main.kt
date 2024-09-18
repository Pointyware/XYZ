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
import org.koin.core.component.get
import org.pointyware.xyz.core.common.di.ApplicationComponent
import org.pointyware.xyz.desktop.di.desktopModule
import org.pointyware.xyz.shared.XyzApp
import org.pointyware.xyz.shared.di.AppDependencies
import org.pointyware.xyz.shared.di.setupKoin

fun main() = application {
    // Configure Koin Dependency Injection (actually Service Locator)
    setupKoin(platformModule = desktopModule())

    val appComponent = ApplicationComponent()

    val appDependencies = appComponent.get<AppDependencies>()

    val state = rememberWindowState()
    Window(
        title = "My Application",
        state = state,
        onCloseRequest = this::exitApplication
    ) {
        XyzApp(
            dependencies = appDependencies,
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
