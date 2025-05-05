package org.pointyware.xyz.build

import org.gradle.api.Project

/**
 * Defines the available values for project build status.
 */
enum class BuildStatus {
    Debug,
    Release
}

/**
 * Executes the given action only if the project status is [BuildStatus.Debug].
 */
fun Project.debug(action: ()->Unit) {
    if (status == BuildStatus.Debug) {
        action()
    }
}

/**
 * Executes the given action only if the project status is [BuildStatus.Release].
 */
fun Project.release(action: ()->Unit) {
    if (status == BuildStatus.Release) {
        action()
    }
}
