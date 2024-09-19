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
import org.pointyware.xyz.core.data.LifecycleController
import org.pointyware.xyz.desktop.di.desktopModule
import org.pointyware.xyz.shared.XyzApp
import org.pointyware.xyz.shared.di.AppDependencies
import org.pointyware.xyz.shared.di.setupKoin

/**
 * Main entry point for the desktop application.
 */
fun main() {

    // Configure Koin Dependency Injection (actually Service Locator)
    setupKoin(platformModule = desktopModule())

    // Get the dependencies
    val appComponent = ApplicationComponent()
    val appDependencies = appComponent.get<AppDependencies>()

    val lifecycleController = appComponent.scope.get<LifecycleController>()
    lifecycleController.onStart()
    lifecycleController.onResume() // TODO: observe when window is shown/navigated to/focused

    application(exitProcessOnExit = false) {

        val state = rememberWindowState()
        // TODO: observe window state changes for configuration scope

        Window(
            title = "My Application",
            state = state,
            onCloseRequest = {
                appComponent.finish()
                exitApplication()
            }
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

    lifecycleController.onPause() // TODO: observe when window is hidden/navigated away from/unfocused
    lifecycleController.onStop()
}
