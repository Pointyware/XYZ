package org.pointyware.xyz.api.model

/**
 *
 */
data class UserCredentials(
    val email: String,
    val hash: String,
    val salt: String,
)
