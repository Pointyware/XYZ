/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common

/**
 * Controls application logic at build-time to allow branch shaking to remove unused code.
 */
object BuildInfo {
    val buildType = BuildType.DEBUG

    val isDebug: Boolean = buildType == BuildType.DEBUG
}
