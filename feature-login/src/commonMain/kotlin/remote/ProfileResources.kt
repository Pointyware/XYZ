/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import io.ktor.resources.Resource

/**
 *
 */
@Resource("profile")
sealed interface Profile {
    @Resource("{userId}")
    data class Id(val userId: String)
}
