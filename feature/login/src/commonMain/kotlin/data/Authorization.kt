/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.feature.login.data

/**
 * Separates authorization information from the profile entity.
 */
interface Authorization {
    /**
     * User email.
     */
    val email: String

    /**
     * User token.
     */
    val token: String

    class InUseException(val email: String): Exception("Email ($email) is already in use.")
    class InvalidCredentialsException: Exception("Invalid email or password.")
}
