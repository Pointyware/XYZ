/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common

/**
 * A build type that controls application logic at build-time to allow
 * branch shaking to remove unused code, e.g. logging, debugging, etc.
 */
enum class BuildType {
    DEBUG,
    RELEASE
}
