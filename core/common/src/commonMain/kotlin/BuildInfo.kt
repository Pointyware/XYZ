/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common

/**
 * Controls application logic at build-time to allow branch shaking to remove unused code.
 */
object BuildInfo {
    enum class BuildType {
        DEBUG,
        RELEASE
    }
    val buildType = BuildType.DEBUG

    fun isDebug(): Boolean = buildType == BuildType.DEBUG
}
