package org.pointyware.xyz.core.entities

import org.pointyware.xyz.core.common.Regex
/**
 * TODO: describe purpose/intent of PhoneNumber
 */
data class PhoneNumber(
    @Regex("^[0-9]+$")
    val sequence: String
)
