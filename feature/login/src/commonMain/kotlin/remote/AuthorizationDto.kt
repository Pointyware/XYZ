/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote

import org.pointyware.xyz.feature.login.data.Authorization

/**
 *
 */
data class AuthorizationDto(
    override val email: String,
    override val token: String,
): Authorization