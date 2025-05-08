package org.pointyware.xyz.core.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LoginInfo(
    val email: String,
    val password: String,
)
