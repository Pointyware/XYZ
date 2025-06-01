package org.pointyware.xyz.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LoginInfo(
    val email: String,
    val password: String,
)
