/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.data

import org.pointyware.xyz.core.entities.Profile
import org.pointyware.xyz.feature.login.data.Authorization

/**
 * Combines the general authorization data with the user profile.
 */
data class Login(
    val authorization: Authorization,
    val profile: Profile?
)
