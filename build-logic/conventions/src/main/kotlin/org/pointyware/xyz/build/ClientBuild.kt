/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.build

import org.gradle.api.Project

/**
 * Defines the available values for project build status.
 */
enum class ClientBuild {
    Debug,
    Release
}

/**
 * Executes the given action only if the project status is [ClientBuild.Debug].
 */
fun Project.debug(action: ()->Unit) {
    if (status == ClientBuild.Debug) {
        action()
    }
}

/**
 * Executes the given action only if the project status is [ClientBuild.Release].
 */
fun Project.release(action: ()->Unit) {
    if (status == ClientBuild.Release) {
        action()
    }
}
