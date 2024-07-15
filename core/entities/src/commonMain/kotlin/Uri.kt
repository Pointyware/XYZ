package org.pointyware.xyz.core.entities

/**
 *
 */
class Uri(
    val value: String
) {

    init {
        require(value.isNotBlank()) { "Uri cannot be blank" }
    }
}
