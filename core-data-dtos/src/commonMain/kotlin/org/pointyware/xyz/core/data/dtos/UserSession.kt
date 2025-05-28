package org.pointyware.xyz.core.data.dtos

import kotlinx.serialization.Serializable

/**
 * Represents a user session, containing the user ID and session ID.
 *
 * @param userId The UUID hex string of the user.
 * @param sessionId The UUID hex string of the session.
 */
@Serializable
data class UserSession(
    val userId: String,
    val sessionId: String
)
