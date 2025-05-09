package org.pointyware.xyz.core.data.dtos

import kotlinx.serialization.Serializable

/**
 * Represents a user authorization, identified by the user account email, and authorized by a token
 * after authenticating with password.
 */
@Serializable
data class Authorization(
    val email: String,
    val token: String
)
