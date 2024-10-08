/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.data

import org.pointyware.xyz.core.entities.Uuid

/**
 * Separates authorization information from the profile entity.
 */
interface Authorization {
    /**
     * User email.
     */
    val userId: Uuid

    /**
     * User token.
     */
    val token: String

    class InUseException(val email: String): Exception("Email ($email) is already in use.")
    class InvalidCredentialsException: Exception("Invalid email or password.")
}
