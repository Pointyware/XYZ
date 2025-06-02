package org.pointyware.xyz.api.dtos

import kotlinx.serialization.Serializable

@Serializable
open class ServerError(
    val message: String
)
