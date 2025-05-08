package org.pointyware.xyz.core.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class Authorization(
    val token: String
) {
    companion object {
        val Failure = Authorization("Failure")
    }
}
