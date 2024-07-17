/*
 * Copyright (c) 2024 Pointyware
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
