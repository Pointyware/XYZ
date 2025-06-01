package org.pointyware.xyz.api.dtos

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents a user authorization, identified by the user account email, and authorized by a token
 * after authenticating with password.
 */
@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Authorization(
    val userId: Uuid,
    val token: String
)
